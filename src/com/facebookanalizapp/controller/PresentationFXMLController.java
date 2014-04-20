/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.chart.BarChart;
import com.facebookanalizapp.chart.Chart;
import com.facebookanalizapp.chart.PieChart;
import com.facebookanalizapp.chart.Table;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ufuk
 */
public class PresentationFXMLController implements Initializable {

    private static final String STR_TABLE = "TABLO";
    private static final String STR_PIE_CHART = "PIE CHART";
    private static final String STR_BAR_CHART = "BAR CHART";
    
    
    private static final int TABLE = 1;
    private static final int PIE_CHART = 2;
    private static final int BAR_CHART = 3;
    
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
        instance = this;
    }

    @FXML
    private void onSelect(ActionEvent event) {
        String selected = (String)cmbPresentation.getSelectionModel().getSelectedItem();
        System.out.println("value : " + selected);
        Presentation p = new Presentation();
        p.setName(selected);
        p.setChartType(setChartType(selected));
        p.setChart(null);
        parentNode.setPresentation(p);
        closeWindow();
    }
    
    @FXML
    private void onSave(ActionEvent event) {

    }
    
    private void closeWindow(){
        Stage stage = (Stage) cmbPresentation.getScene().getWindow();
        stage.close();
    }
        
    private int setChartType(String selected){
        if (selected.equalsIgnoreCase(STR_TABLE)) {
            return TABLE;
        }else if (selected.equalsIgnoreCase(STR_BAR_CHART)) {
            return BAR_CHART;
        }else if (selected.equalsIgnoreCase(STR_PIE_CHART)) {
            return PIE_CHART;
        }
        return -1;
    }
}
