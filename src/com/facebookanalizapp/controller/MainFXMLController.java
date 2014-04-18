/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.entitymanager.EntityManagerService;
import com.facebookanalizapp.process.DBProperty;
import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.FileTool;
import com.facebookanalizapp.process.Node;
import com.facebookanalizapp.process.PropertyManager;
import com.facebookanalizapp.ui.NodeUI;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        refreshDatabasesList();
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
    }

    @FXML
    private void onExport(ActionEvent event) {

    }

    @FXML
    private void onImport(ActionEvent event) {

    }

    @FXML
    private void onAddAction(ActionEvent event) {//Node ekleme
        String nodeName = Dialogs.showInputDialog(null, "Node ismi giriniz:", "Node Kayıt", "Kayıt Ekranı");
        Node node = new Node(nodeName, 150,150);
        pane.getChildren().add(node.getNdUi());
        scrollMain.setContent(pane);
    }

    @FXML
    private void onActionDelete(ActionEvent event) {//Node db'den silme

    }

    @FXML
    private void onChanged(ActionEvent event) {
        if (!((String) cmbDatabases.getValue()).equalsIgnoreCase(NO_DB_SELECTED)) {
            EntityManagerService.clearDB();
            DBProperty db = getSelectedDB((String) cmbDatabases.getValue());
            EntityManagerService.setPersistenceMap(db.getDbPath() + File.separator + db.getDbName(), "facebookapp", "facebookapp");
            EntityManager manager = EntityManagerService.get().createEntityManager();
            if (manager != null) {
                Dialogs.showInformationDialog((Stage) cmbDatabases.getScene().getWindow(), db.getDbName() + " adlı veritabanı seçildi!", "İşlem Başarılı", "Bilgi");
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

    public void refreshDatabasesList() {
        cmbDatabases.getItems().clear();
        cmbDatabases.getItems().add(NO_DB_SELECTED);
        list = PropertyManager.instance().getAllDatabasesFromPropertyFile();
        if (list.size() > 0 && list != null) {
            for (int i = 0; i < list.size(); i++) {
                cmbDatabases.getItems().add(list.get(i).getDbName());
            }
        }
        cmbDatabases.getSelectionModel().selectFirst();
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
