/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.JsonReader;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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

        for (Iterator<String> it = setAttrList.iterator(); it.hasNext();) {
            String string = it.next();
            System.out.println("value : " + string);
        }
        ObservableList<String> items = FXCollections.observableArrayList(setAttrList);
        lstViewAttrDB.setItems(items);
    }

    @FXML
    private void onKMeansSelect(ActionEvent event) {

    }

    @FXML
    private void onScanSelect(ActionEvent event) {

    }

    @FXML
    private void onClustSelect(ActionEvent event) {

    }

    @FXML
    private void onGetSelectedClustering(ActionEvent event) {

    }

    @FXML
    private void onRemove(ActionEvent event) {

    }

    @FXML
    private void onAdd(ActionEvent event) {

    }

    @FXML
    private void onSaveClustering(ActionEvent event) {

    }
}
