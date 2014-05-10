/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.db.DatabaseManager;
import com.facebookanalizapp.entity.DataEntity;
import com.facebookanalizapp.entity.ExecutedRulesEntity;
import com.facebookanalizapp.entitymanager.EntityManagerService;
import com.facebookanalizapp.process.DBProperty;
import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.Node;
import com.facebookanalizapp.process.PropertyManager;
import com.facebookanalizapp.ui.NodeUI;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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
        //setNodeDragEvent();
        refreshDatabasesList(true);
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
        /*if (!((String) cmbDatabases.getValue()).equalsIgnoreCase(NO_DB_SELECTED)) {
         EntityManagerService.clearDB();
         DBProperty db = getSelectedDB((String) cmbDatabases.getValue());
         EntityManagerService.setPersistenceMap(db.getDbPath() + File.separator + db.getDbName(), "facebookapp", "facebookapp");
         EntityManager manager = EntityManagerService.get().createEntityManager();
         if (manager != null) {
         Dialogs.showInformationDialog((Stage) cmbDatabases.getScene().getWindow(), db.getDbName() + " adlı veritabanı seçildi!", "İşlem Başarılı", "Bilgi");
         }
         getNodesFromDB();
         }*/
    }

    private void getNodesFromDB() {
        if (EntityManagerService.emfInstance != null) {
            List<ExecutedRulesEntity> lstAd = DatabaseManager.instance().getEntityList(ExecutedRulesEntity.class, "ExecutedRulesEntity.findAll");
            if (lstAd != null && lstAd.size() > 0) {
                listViewNodes.setItems(FXCollections.observableList(lstAd));
                listViewNodes.setCellFactory(new Callback<ListView<ExecutedRulesEntity>, ListCell<ExecutedRulesEntity>>() {

                    @Override
                    public ListCell<ExecutedRulesEntity> call(ListView<ExecutedRulesEntity> p) {

                        ListCell<ExecutedRulesEntity> cell = new ListCell<ExecutedRulesEntity>() {

                            @Override
                            protected void updateItem(ExecutedRulesEntity t, boolean bln) {
                                super.updateItem(t, bln);
                                if (t != null) {
                                    setText(t.getName());
                                }
                            }

                        };

                        return cell;
                    }
                });
                System.out.println("Node var!!");
            } else {
                System.out.println("Node yok!!");
            }
        }
    }
    /*private void setNodeDragEvent(){
     testCircle.setOnMouseDragged(new EventHandler<MouseEvent>() {

     @Override
     public void handle(MouseEvent t) {
     //System.out.println("x : " + t.getScreenX());
     }
     });
        
     testCircle.setOnMouseReleased(new EventHandler<MouseEvent>() {

     @Override
     public void handle(MouseEvent t) {
     NodeUI tmp = new NodeUI(300, 200);
     nodeListGLOBAL.add(tmp);
     pane.getChildren().add(tmp);

     scrollMain.setContent(pane);
     }
     });
     }*/

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
                //cmbDatabases.getItems().add(list.get(i).getDbName());
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
