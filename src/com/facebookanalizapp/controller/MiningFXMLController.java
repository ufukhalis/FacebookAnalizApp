/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.controller;

import java.net.URL;
import java.util.ResourceBundle;
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

    /**
     * Initializes the controller class.
     */
    
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
        // TODO
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
