/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.db.DatabaseManager;
import com.facebookanalizapp.entity.ClusteringEntity;
import com.facebookanalizapp.entity.CosineEntity;
import com.facebookanalizapp.entity.DataEntity;
import com.facebookanalizapp.entity.ExecutedRulesEntity;
import com.facebookanalizapp.entity.MiningEntity;
import com.facebookanalizapp.entity.PresentationEntity;
import com.facebookanalizapp.entitymanager.EntityManagerService;
import com.facebookanalizapp.mining.KMeans;
import com.facebookanalizapp.process.DBProperty;
import com.facebookanalizapp.process.Data;
import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.Mining;
import com.facebookanalizapp.process.Node;
import com.facebookanalizapp.process.Presentation;
import com.facebookanalizapp.process.PropertyManager;
import com.facebookanalizapp.process.Utility;
import com.facebookanalizapp.ui.NodeUI;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author ufuk
 */
public class MainFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String NO_DB_SELECTED = "Seçim yapılmadı";

    private static MainFXMLController instance;

    @FXML
    ComboBox cmbDatabases;

    @FXML
    ScrollPane scrollMain;

    @FXML
    ListView listViewNodes;

    Pane pane = new Pane();

    private List<DBProperty> list = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        refreshDatabasesList(true);
        listViewNodes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (t.getButton().equals(MouseButton.PRIMARY)) {
                    if (t.getClickCount() == 2) {
                        ExecutedRulesEntity execNode = (ExecutedRulesEntity) listViewNodes.getSelectionModel().getSelectedItem();
                        MiningEntity me = DatabaseManager.instance().find(MiningEntity.class, execNode.getMiningID());
                        PresentationEntity pe = DatabaseManager.instance().find(PresentationEntity.class, execNode.getPresentationID());
                        DataEntity de = DatabaseManager.instance().find(DataEntity.class, execNode.getDataID());
                        ClusteringEntity ce = DatabaseManager.instance().find(ClusteringEntity.class, me.getClusteringID());
                        CosineEntity coe = DatabaseManager.instance().find(CosineEntity.class, me.getCosineID());

                        Data d = new Data();
                        d.setName(de.getName());
                        d.setJsonDataList(Utility.instance().getListFromRaw(de.getRawData(), "#"));

                        Mining m = new Mining();
                        m.setName(me.getName());
                        m.setMininType(me.getCosineID().intValue());
                        m.setK(me.getK());
                        m.setLoop(me.getLoop());

                        if (me.getClusteringID() != 0) {
                            for (String rule : Utility.instance().getListFromRaw(ce.getAttributeList(), ",")) {
                                m.setClusteringSelectedRulesList(rule);
                            }
                            m.setMininType(1);
                        } else if (me.getCosineID() != 0) {
                            List<KMeans> kmeansLst = new ArrayList<>();
                            for (String rule : Utility.instance().getListFromRaw(coe.getValues(), "#")) {
                                KMeans k = new KMeans();
                                k.setPersonName(rule.substring(0, rule.indexOf(",")));
                                k.setKmeansName(rule.substring(rule.indexOf(",") + 1));
                                kmeansLst.add(k);
                            }
                            m.setKmeansPresentationData(kmeansLst);
                            m.setMininType(2);
                        }

                        Presentation p = new Presentation();
                        p.setName(pe.getPresentationName());
                        p.setChart(null);
                        p.setChartType(pe.getChartType());

                        Node node = new Node(execNode.getName(), 150, 150);
                        node.setData(d);
                        node.setMining(m);
                        node.setPresentation(p);
                        node.getNdUi().branch1.setInfo(d.getName());
                        node.getNdUi().branch2.setInfo(m.getName());
                        node.getNdUi().branch3.setInfo(p.getName());
                        node.getNdUi().nodeId = execNode.getId();
                        pane.getChildren().add(node.getNdUi());
                        scrollMain.setContent(pane);
                    }
                }
            }
        });
    }

    public static MainFXMLController instance() {
        return instance;
    }

    @FXML
    private void onAdd(ActionEvent event) {
        FXMLTool.instance().openFXML("Veritabanı Ekle", "DatabaseFXML.fxml", false);
    }

    @FXML
    private void onDelete(ActionEvent event) {
        EntityManagerService.close((String) cmbDatabases.getValue());
        refreshDatabasesList(true);
    }

    @FXML
    private void onExport(ActionEvent event) {
        DirectoryChooser dic = new DirectoryChooser();
        File file = dic.showDialog(null);
        if (file != null) {
            System.out.println("Source : " + (selectedDB.getDbPath() + File.separator + selectedDB.getDbName()));
            File source = new File(selectedDB.getDbPath() + File.separator + selectedDB.getDbName());
            File desc = new File(file.getPath() + File.separator + selectedDB.getDbName());
            System.out.println("Dest : " + file.getPath());
            Dialogs.showInformationDialog((Stage) cmbDatabases.getScene().getWindow(), "Export işlemi başarılı şekilde tamamlandı!",
                    "İşlem başarılı", "Bilgi");
            try {
                FileUtils.copyDirectory(source, desc);
            } catch (IOException e) {
                System.out.println("Error exporting db : " + e);
            }
        }
    }

    @FXML
    private void onImport(ActionEvent event) {
        DirectoryChooser dic = new DirectoryChooser();
        File file = dic.showDialog(null);
        if (file != null) {
            String path = (file.getPath().substring(0, file.getPath().indexOf(file.getName())));
            String name = file.getName();
            EntityManagerService.setPersistenceMap(path + name, "facebookapp", "facebookapp");
            EntityManager manager = EntityManagerService.get().createEntityManager();

            if (manager != null) {
                PropertyManager.instance().addDBPropertiesFile(path, name);
                refreshDatabasesList(false); //Bu sorun çıkartıyor!!
                /*Dialogs.showInformationDialog((Stage) txtDBName.getScene().getWindow(), txtDBName.getText() + " adlı veritabanı başarılı şekilde oluşturuldu!",
                 "İşlem başarılı", "Bilgi");*/
            } else {
                /*Dialogs.showErrorDialog((Stage) txtDBName.getScene().getWindow(), "Veritabanı oluşturulamadı!",
                 "Bir hata ile karşılaşıldı", "Hata", new Exception());*/
            }
        }
    }

    @FXML
    private void onAddAction(ActionEvent event) {//Node ekleme
        String nodeName = Dialogs.showInputDialog(null, "Node ismi :", "Oluşturacağınız Node'un ismini giriniz.", "Kayıt Ekranı");
        if (!nodeName.trim().isEmpty()) {
            Node node = new Node(nodeName, 150, 150);
            pane.getChildren().add(node.getNdUi());
            scrollMain.setContent(pane);
        }
    }

    @FXML
    private void onActionDelete(ActionEvent event) {//Node db'den silme
        ExecutedRulesEntity execNode = (ExecutedRulesEntity) listViewNodes.getSelectionModel().getSelectedItem();
        MiningEntity mEntity = DatabaseManager.instance().find(MiningEntity.class, execNode.getMiningID());
        CosineEntity cosEntity = DatabaseManager.instance().find(CosineEntity.class, mEntity.getCosineID());
        ClusteringEntity cEntity = DatabaseManager.instance().find(ClusteringEntity.class, mEntity.getCosineID());

        DatabaseManager.instance().removeEntity(DatabaseManager.instance().find(DataEntity.class, execNode.getDataID()));
        DatabaseManager.instance().removeEntity(cosEntity);
        DatabaseManager.instance().removeEntity(cEntity);
        DatabaseManager.instance().removeEntity(mEntity);
        DatabaseManager.instance().removeEntity(DatabaseManager.instance().find(PresentationEntity.class, execNode.getPresentationID()));
        DatabaseManager.instance().removeEntity(execNode);
        getNodesFromDB();
    }

    private static DBProperty selectedDB;

    @FXML
    private void onSelectDB(ActionEvent event) {//DB Seçme işlemi
        if (!((String) cmbDatabases.getValue()).equalsIgnoreCase(NO_DB_SELECTED)) {
            EntityManagerService.clearDB();
            DBProperty db = getSelectedDB((String) cmbDatabases.getValue());
            selectedDB = db;
            EntityManagerService.setPersistenceMap(db.getDbPath() + File.separator + db.getDbName(), "facebookapp", "facebookapp");
            EntityManager manager = EntityManagerService.get().createEntityManager();
            if (manager != null) {
                Dialogs.showInformationDialog((Stage) cmbDatabases.getScene().getWindow(), db.getDbName() + " adlı veritabanı seçildi!", "İşlem Başarılı", "Bilgi");
            }
            getNodesFromDB();
        }
    }

    @FXML
    private void onChanged(ActionEvent event) {
    }

    public void getNodesFromDB() {
        DatabaseManager.instance().fillListViewFromDB(listViewNodes, ExecutedRulesEntity.class, "ExecutedRulesEntity.findAll");
    }

    public void removeNodeFromPane(NodeUI node) {
        pane.getChildren().remove(node);
        scrollMain.setContent(pane);
    }

    public void refreshDatabasesList(boolean isInit) {
        List<String> cmbDBlst = new ArrayList<>();
        cmbDBlst.add(NO_DB_SELECTED);
        list = PropertyManager.instance().getAllDatabasesFromPropertyFile();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                cmbDBlst.add(list.get(i).getDbName());
            }
            cmbDatabases.setItems(FXCollections.observableList(cmbDBlst));
        } else {
            cmbDatabases.setItems(FXCollections.observableList(cmbDBlst));
        }

        if (isInit) {
            cmbDatabases.getSelectionModel().selectFirst();
        } else {
            cmbDatabases.getSelectionModel().selectLast();
        }
    }

    private DBProperty getSelectedDB(String dbName) {
        List<DBProperty> lst = PropertyManager.instance().getAllDatabasesFromPropertyFile();
        for (DBProperty dBProperty : lst) {
            if (dBProperty.getDbName().equalsIgnoreCase(dbName)) {
                return dBProperty;
            }
        }
        return null;
    }
}
