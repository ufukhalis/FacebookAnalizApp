/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.db.DatabaseManager;
import com.facebookanalizapp.entity.DataEntity;
import com.facebookanalizapp.process.Data;
import com.facebookanalizapp.process.ExcelReader;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        viewData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getDatasFromDB();
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

    public TextField getTxtPath() {
        return txtPath;
    }

    @FXML
    private void onEdit(ActionEvent event) {
        int index = viewData.getSelectionModel().getSelectedIndex();
        //System.out.println("value : " + value);

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

        controller.initData();// InitData Çağırılarak viewData'da seçili olan satır editorde gösterilir.

        Scene secondScene = new Scene(parent);
        Stage secondStage = new Stage();
        secondStage.setTitle("Veri Düzenle");
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);

        secondStage.show();
    }

    private Long selectedDataId = -1l;

    @FXML
    private void onSave(ActionEvent event) {
        String raw = "";
        for (int i = 0; i < viewData.getItems().size(); i++) {
            raw += viewData.getItems().get(i).getData() + "#";
        }
        System.out.println("Raw : " + raw);
        DataEntity dataEntity = null; 
        if(selectedDataId != -1){
            dataEntity = DatabaseManager.instance().find(DataEntity.class, selectedDataId);
        }                
        if (dataEntity != null) {
            dataEntity.setName(txtDataName.getText());
            dataEntity.setRawData(raw);
            applyToParentNode();
            DatabaseManager.instance().updateEntity(DataEntity.class, dataEntity);
        } else {
            dataEntity = new DataEntity();
            dataEntity.setName(txtDataName.getText());
            dataEntity.setRawData(raw);
            applyToParentNode();
            DatabaseManager.instance().saveEntity(dataEntity);
        }
        getDatasFromDB();
    }

    @FXML
    private void onGetData(ActionEvent event) {
        viewData.getItems().clear();
        DataEntity e = (DataEntity) lstViewData.getSelectionModel().getSelectedItem();
        System.out.println("Value : " + e.getName());
        String[] list = e.getRawData().split("#");
        for (String string : list) {
            if (JsonReader.isValid(string)) {
                JSonData item = new JSonData();
                item.data.setValue(string);
                data.add(item);
            }
        }
        viewData.setItems(data);
        txtDataName.setText(e.getName());
        selectedDataId = e.getId();
    }

    @FXML
    private void onDeleteTable(ActionEvent event) {
        List items = new ArrayList(viewData.getSelectionModel().getSelectedItems());
        viewData.getItems().removeAll(items);
        viewData.getSelectionModel().clearSelection();
    }

    @FXML
    private void onDeleteDB(ActionEvent event) {
        DataEntity e = (DataEntity) lstViewData.getSelectionModel().getSelectedItem();
        DatabaseManager.instance().removeEntity(e);
        getDatasFromDB();
    }

    private void getDatasFromDB() {
        DatabaseManager.instance().fillListViewFromDB(lstViewData, DataEntity.class, "DataEntity.findAll");
    }

    @FXML
    private void onApply(ActionEvent event) {
        applyToParentNode();

        String info = getTxtPath().getText();
        parentNode.getNdUi().getBranch1().getLblInfo().textProperty().setValue(!info.isEmpty() ? info : parentNode.getNdUi().getBranch1().getInfo());
        onDone(event);
    }

    private void applyToParentNode() {
        Data dataShare = new Data();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < viewData.getItems().size(); i++) {
            list.add(viewData.getItems().get(i).getData());
        }
        dataShare.setJsonDataList(list);
        dataShare.setName(txtDataName.getText());
        parentNode.setData(dataShare);
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
