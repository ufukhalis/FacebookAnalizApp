/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.controller;

import com.facebookanalizapp.ui.NodeUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ufuk
 */
public class MainFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    ComboBox cmbDatabases;
    
    /*@FXML
    Pane paneMain;*/
    @FXML
    ScrollPane scrollMain;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    private void onAdd(ActionEvent event) {
        
        /*paneMain.getChildren().clear();
        paneMain.getChildren().add(new NodeUI(200, 200));*/
        Pane pane = new Pane();
        pane.getChildren().add(new NodeUI(200, 200));
        
        scrollMain.setContent(pane);
    }
    
    @FXML
    private void onDelete(ActionEvent event) {

    }
    
    @FXML
    private void onExport(ActionEvent event) {

    }
    
    @FXML
    private void onImport(ActionEvent event) {

    }
}
