/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.Node;
import com.facebookanalizapp.process.Presentation;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author ufuk
 */
public class PresentationFXMLController implements Initializable {

    private static PresentationFXMLController instance = null;
    
    public Node parentNode;
    
    public static PresentationFXMLController instance(){
        return instance;
    }
   
    @FXML
    Button btnSelect;

    @FXML
    Button btnSave;

    @FXML
    ComboBox cmbPresentation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onSelect(ActionEvent event) {
        Presentation p = new Presentation();
        p.setName("");
        p.setChartType(Integer.MIN_VALUE);
        p.setChart(null);
        parentNode.setPresentation(p);
    }
    
    @FXML
    private void onSave(ActionEvent event) {

    }
}
