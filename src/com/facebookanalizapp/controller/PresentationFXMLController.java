/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.controller;

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

    /**
     * Initializes the controller class.
     */
    
    private static final String[] listCombo = {"TABLO", "BAR CHART", "PIE CHART"};
    
    @FXML
    Button btnSelect;
    
    @FXML
    ComboBox cmbPresentation;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
 
    
    @FXML
    private void onSelect(ActionEvent event) {

    }
}
