/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.entitymanager;

import java.util.HashMap;
import java.util.Map;
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

    public static void clearDB(){
        emfInstance = null;
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
