/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.entitymanager;

import com.facebookanalizapp.process.FileTool;
import com.facebookanalizapp.process.PropertyManager;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ufuk
 */
public class EntityManagerService {

    private static Map<String, String> persistenceMap;

    public static EntityManagerFactory emfInstance = null;

    private EntityManagerService() {
    }

    public static EntityManagerFactory get() {
        if (emfInstance == null) {
            emfInstance = Persistence
                    .createEntityManagerFactory("FacebookAnalizAppPU", persistenceMap);
            return emfInstance;
        }

        return emfInstance;
    }

    public static void clearDB() {
        emfInstance = null;
    }

    public static void close(String dbName) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(EntityManagerService.class.getName()).log(Level.SEVERE, null, ex);
            String directory = PropertyManager.instance().removeDBFromPropertiesFile(dbName);
            System.out.println("value : " + directory);
            FileTool.instance().deleteDirectory(directory);
            System.out.println("value2 : " + directory);
            clearDB();
            //MainFXMLController.instance().refreshDatabasesList(false); //Sorun çıkarıyor.
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EntityManagerService.class.getName()).log(Level.SEVERE, null, ex);
        }

        //emfInstance.close();
    }

    public static void setPersistenceMap(String databasePath, String username, String password) {
        persistenceMap = new HashMap<>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:derby:" + databasePath + ";create=true");
        persistenceMap.put("javax.persistence.jdbc.user", username);
        persistenceMap.put("javax.persistence.jdbc.password", password);
        persistenceMap.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        persistenceMap.put("javax.persistence.schema-generation.database.action", "create");
        persistenceMap.put("eclipselink.ddl-generation.output-mode", "database");
    }
}
