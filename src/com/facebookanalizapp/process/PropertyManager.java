
package com.facebookanalizapp.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 *
 * @author hp1
 */
public class PropertyManager {

    public static File propertiesFile = null;
    private static final String propertyFileName = "properties.xml";

    private static PropertyManager property = null;

    private PropertyManager() {
    }

    public synchronized static PropertyManager instance() {
        if (property == null) {
            property = new PropertyManager();
        }
        return property;
    }

    public void createOrReadPropertiesFile() {
        try {

            propertiesFile = new File(propertyFileName);

            if (!propertiesFile.exists()) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("properties");
                doc.appendChild(rootElement);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("properties.xml"));

                transformer.transform(source, result);
            }
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void addDBPropertiesFile(String path, String name) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(propertiesFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            Element database = doc.createElement("database");
            rootElement.appendChild(database);

            Element dbPath = doc.createElement("path");
            dbPath.appendChild(doc.createTextNode(path));
            database.appendChild(dbPath);

            Element dbName = doc.createElement("name");
            dbName.appendChild(doc.createTextNode(name));
            database.appendChild(dbName);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(propertiesFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String removeDBFromPropertiesFile(String dbName) {
        try {
            String fullPath = "";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(propertiesFile);

            NodeList databaseList = doc.getElementsByTagName("database");

            for (int i = 0; i < databaseList.getLength(); i++) {
                NodeList list = databaseList.item(i).getChildNodes();
                for (int j = 0; j < list.getLength(); j++) {
                    Node node = list.item(j);
                    if (dbName.equals(node.getTextContent())) {                        
                        databaseList.item(i).getParentNode().removeChild(databaseList.item(i));
                        fullPath = list.item(j - 1).getTextContent() + File.separator + dbName;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(propertiesFile);
            transformer.transform(source, result);
            return fullPath;
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<DBProperty> getAllDatabasesFromPropertyFile() {
        List<DBProperty> list = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(propertyFileName));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("database");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String path = eElement.getElementsByTagName("path").item(0).getTextContent();
                    System.out.println("name : " + name + " path : " + path);
                    list.add(new DBProperty(name, path));
                }
            }

        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return list;
    }
}
