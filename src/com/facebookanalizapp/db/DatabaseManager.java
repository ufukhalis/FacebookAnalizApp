/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.db;

import com.facebookanalizapp.entitymanager.EntityManagerService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author hp1
 */
public class DatabaseManager {
    private static DatabaseManager instance = null;
    
    EntityManager manager = null;
    
    public static synchronized DatabaseManager instance(){
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    public <T> T saveEntity(T entity){
        connect();
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.flush();
        manager.getTransaction().commit();
        close();
        return entity;
    }
    
    public <T> T updateEntity(Class<T> clazz, T entity){
        connect();
        manager.getTransaction().begin();
        manager.merge(entity);
        manager.getTransaction().commit();
        close();
        return entity;
    }
    
    public <T> T find(Class<T> clazz, Long id){
        connect();
        T entity = manager.find(clazz, id);
        close();
        return entity;
    }
    
    public <T> List<T> getEntityList(Class<T> entity, String query){
        connect();
        manager.getTransaction().begin();
        Query q = manager.createNamedQuery(query, entity);
        List<T> lst = q.getResultList();
        manager.getTransaction().commit();
        close();
        return lst;
    }
    
    public <T> void removeEntity(T entity){
        connect();
        manager.getTransaction().begin();
        manager.remove(entity);
        manager.getTransaction().commit();
        close();
    }
    
    private void connect(){
        manager = EntityManagerService.get().createEntityManager();
    }
    
    private void close(){
        manager.close();
    }
}
