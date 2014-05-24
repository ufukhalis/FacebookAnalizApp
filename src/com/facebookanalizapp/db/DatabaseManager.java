
package com.facebookanalizapp.db;

import com.facebookanalizapp.entitymanager.EntityManagerService;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author hp1
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;

    EntityManager manager = null;

    public static synchronized DatabaseManager instance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public <T> T saveEntity(T entity) {
        connect();
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.flush();
        manager.getTransaction().commit();
        close();
        return entity;
    }

    public <T> T updateEntity(Class<T> clazz, T entity) {
        connect();
        manager.getTransaction().begin();
        manager.merge(entity);
        manager.getTransaction().commit();
        close();
        return entity;
    }

    public <T> T find(Class<T> clazz, Long id) {
        connect();
        T entity = manager.find(clazz, id);
        close();
        return entity;
    }

    public <T> List<T> getEntityList(Class<T> entity, String query) {
        connect();
        manager.getTransaction().begin();
        Query q = manager.createNamedQuery(query, entity);
        List<T> lst = q.getResultList();
        manager.getTransaction().commit();
        close();
        return lst;
    }

    public <T> void removeEntity(T entity) {
        if (entity != null) {
            connect();
            manager.getTransaction().begin();
            T mEntity = manager.merge(entity);
            if (mEntity != null) {
                manager.remove(mEntity);
            }
            manager.getTransaction().commit();
            close();
        }
    }

    private void connect() {
        manager = EntityManagerService.get().createEntityManager();
    }

    private void close() {
        manager.close();
    }

    public <T> void fillListViewFromDB(ListView lstVeiew, Class<T> clazz, String query) {
        if (EntityManagerService.emfInstance != null) {
            lstVeiew.getItems().clear();
            List<T> lstAd = DatabaseManager.instance().getEntityList(clazz, query);
            if (lstAd != null && lstAd.size() > 0) {
                lstVeiew.setItems(FXCollections.observableList(lstAd));
                lstVeiew.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {

                    @Override
                    public ListCell<T> call(ListView<T> p) {

                        ListCell<T> cell = new ListCell<T>() {

                            @Override
                            protected void updateItem(T t, boolean bln) {
                                super.updateItem(t, bln);
                                if (t != null) {
                                    setText(t.toString());
                                }
                            }

                        };

                        return cell;
                    }
                });
                System.out.println("Data var!!");
            } else {
                System.out.println("Data yok!!");
            }
        }
    }
}
