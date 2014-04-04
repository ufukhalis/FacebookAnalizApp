/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.ui.NodeUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

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

    @FXML
    Circle testCircle;

    @FXML
    ScrollPane scrollMain;

    Pane pane = new Pane();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNodeDragEvent();        
    }

    @FXML
    private void onAdd(ActionEvent event) {
        FXMLTool.instance().openFXML("Veritabanı Ekle", "DatabaseFXML.fxml", false);
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
    
    private void setNodeDragEvent(){
        testCircle.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                //System.out.println("x : " + t.getScreenX());
            }
        });
        
        testCircle.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                pane.getChildren().add(new NodeUI((int) t.getX(), (int) t.getY()));

                scrollMain.setContent(pane);
            }
        });
    }

}
