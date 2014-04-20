/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.db.DatabaseManager;
import com.facebookanalizapp.entity.DataEntity;
import com.facebookanalizapp.entitymanager.EntityManagerService;
import com.facebookanalizapp.process.Data;
import com.facebookanalizapp.process.ExcelReader;
import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.JsonReader;
import com.facebookanalizapp.process.Node;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author hp1
 */
public class DataFXMLController implements Initializable {

    private static DataFXMLController instance = null;
    public Node parentNode;

    @FXML
    TextField txtDataName;

    @FXML
    ListView lstViewData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        column.setCellValueFactory(
                new PropertyValueFactory<JSonData, String>("data")
        );

        data = FXCollections.observableArrayList();
        viewData.setItems(data);
    }

    public static DataFXMLController instance() {
        return instance;
    }

    public void refreshTable() {
        if (parentNode.getData() != null) {
            for (String string : parentNode.getData().getJsonDataList()) {

                JSonData item = new JSonData();
                item.data.setValue(string);
                data.add(item);
            }
            viewData.setItems(data);
        }
    }

    ObservableList<JSonData> data;

    @FXML
    TableView<JSonData> viewData;

    @FXML
    TableColumn column;

    @FXML
    TextField txtPath;

    @FXML
    private void onBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Seç");
        File file = fileChooser.showOpenDialog(null);

        txtPath.setText(file.getPath());

        ExcelReader reader = new ExcelReader();
        List<String> list = reader.read(txtPath.getText().trim());

        for (String string : list) {
            if (JsonReader.isValid(string)) {
                JSonData item = new JSonData();
                item.data.setValue(string);
                data.add(item);
            }
        }
    }

    @FXML
    private void onEdit(ActionEvent event) {
        String data = viewData.getSelectionModel().getSelectedItem().data.getValue();
        System.out.println("value : " + data);

        Parent parent = null;
        FXMLLoader fxmlLoader = null;
        fxmlLoader = new FXMLLoader(getClass().getResource("/com/facebookanalizapp/fxml/DataEditFXML.fxml"));

        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(DataFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataEditFXMLController controller
                = fxmlLoader.<DataEditFXMLController>getController();

        controller.initData(data);

        Scene secondScene = new Scene(parent);
        Stage secondStage = new Stage();
        secondStage.setTitle("Veri Düzenle");
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);

        secondStage.show();
    }

    @FXML
    private void onSave(ActionEvent event) {

        //EntityManager manager = EntityManagerService.get().createEntityManager();
        //manager.getTransaction().begin();
        
        /*DataEntity entity = new DataEntity();
        entity.setName(txtDataName.getText());
        String raw = "";
        for (int i = 0; i < viewData.getItems().size(); i++) {
            raw += viewData.getItems().get(i).getData() + "#";
        }
        entity.setRawData(raw);
        DatabaseManager.instance().saveEntity(entity);*/
        
        //manager.persist(entity);
        //manager.getTransaction().commit();

        /*Query q = manager.createQuery("select a from DataEntity a");
        List<DataEntity> lstAd = q.getResultList();
        for (DataEntity ad : lstAd) {
            System.out.println("*********************************");
            System.out.println(ad.getName());
            System.out.println(ad.getRawData());
        }*/
        
        List<DataEntity> lstAd = DatabaseManager.instance().getEntityList(DataEntity.class, "findAll");
        lstViewData.setItems(FXCollections.observableList(lstAd));
    }

    @FXML
    private void onGetData(ActionEvent event) {

    }

    @FXML
    private void onApply(ActionEvent event) {
        Data dataShare = new Data();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < viewData.getItems().size(); i++) {
            list.add(viewData.getItems().get(i).getData());
        }
        dataShare.setJsonDataList(list);
        dataShare.setName("");
        parentNode.setData(dataShare);
        onDone(event);
    }

    @FXML
    private void onDone(ActionEvent event) {
        Stage stage = (Stage) txtPath.getScene().getWindow();
        stage.close();
    }

    public class JSonData {

        public SimpleStringProperty data = new SimpleStringProperty();

        public void setData(SimpleStringProperty dataString) {
            this.data = dataString;
        }

        public String getData() {
            return data.get();
        }

    }
}
