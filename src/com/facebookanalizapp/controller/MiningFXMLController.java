/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.JsonReader;
import com.facebookanalizapp.process.Mining;
import com.facebookanalizapp.process.Node;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ufuk
 */
public class MiningFXMLController implements Initializable {

    public Node parentNode;

    private static MiningFXMLController instance = null;

    public static MiningFXMLController instance() {
        return instance;
    }

    @FXML
    Button btnKmeansSelect;
    @FXML
    Button btnScanSelect;
    @FXML
    Button btnClustSelect;
    @FXML
    Button btnSaveClustering;
    @FXML
    Button btnGetSelectedClustering;
    @FXML
    Button btnRemove;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtClusteringName;
    @FXML
    ListView lstViewClusteringDB;
    @FXML
    ListView lstViewSelectedAttr;
    @FXML
    ListView lstViewAttrDB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }

    public void fillAttributeList() {
        //lstViewAttrDB
        Set<String> setAttrList = new HashSet<>();
        try {

            JsonReader jr = new JsonReader();
            for (String object : parentNode.getData().getJsonDataList()) {
                //System.out.println("value : " + object);
                for (String string : jr.getPersonLikes(object)) {
                    setAttrList.add(string);
                    //System.out.println("value : " + string);
                }
            }
        } catch (Exception e) {
        }

        /*for (Iterator<String> it = setAttrList.iterator(); it.hasNext();) {
            String string = it.next();
            System.out.println("value : " + string);
        }*/
        ObservableList<String> items = FXCollections.observableArrayList(setAttrList);
        lstViewAttrDB.setItems(items);
        
        //Seçilen bir liste var ise seçilenler listesini doldur.
        if(parentNode.getMining()!=null){
        
            Mining mng = parentNode.getMining();
            for (String selected : mng.getClusteringSelectedRulesList()) {
                 lstViewAttrDB.getItems().remove(selected);
                 lstViewSelectedAttr.getItems().add(selected);
            }
           lstViewAttrDB.getSelectionModel().clearSelection();
        }
        
    }

    @FXML
    private void onKMeansSelect(ActionEvent event) {

    }

    @FXML
    private void onScanSelect(ActionEvent event) {

    }

    @FXML
    private void onClustSelect(ActionEvent event) {
        Mining mining = new Mining();
        mining.setCosineArray("");
        mining.setMininType(1);
        mining.setPresentationData(null);
        mining.setName(txtClusteringName.getText());
        for (Iterator it = lstViewSelectedAttr.getItems().iterator(); it.hasNext();) {
            String str = it.next().toString();
            mining.setClusteringSelectedRulesList(str);
        }

        parentNode.setMining(mining);
        closeWindow();
    }

    @FXML
    private void onGetSelectedClustering(ActionEvent event) {

    }

    @FXML
    private void onRemove(ActionEvent event) {
        addListToList(lstViewSelectedAttr, lstViewAttrDB);
    }

    @FXML
    private void onAdd(ActionEvent event) {
        addListToList(lstViewAttrDB, lstViewSelectedAttr);
    }

    @FXML
    private void onSaveClustering(ActionEvent event) {

    }

    private void addListToList(ListView from, ListView to) {
        try {
            String selected = from.getSelectionModel().getSelectedItem().toString();
            from.getItems().remove(selected);
            to.getItems().add(selected);
            from.getSelectionModel().clearSelection();
        } catch (Exception e) {
            Dialogs.showErrorDialog(null, "Bir seçim yapmadınız!!");
        }
    }
    
    private void closeWindow(){
        Stage stage = (Stage) lstViewAttrDB.getScene().getWindow();
        stage.close();
    }
}
