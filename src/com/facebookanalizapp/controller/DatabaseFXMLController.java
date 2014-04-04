/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.controller;

import com.facebookanalizapp.entitymanager.EntityManagerService;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author hp1
 */
public class DatabaseFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TextField txtDBPath;
    
    @FXML
    TextField txtDBName;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onCreate(ActionEvent event) {
        if (!txtDBName.getText().isEmpty()) {
            EntityManagerService.setPersistenceMap(txtDBPath.getText() + File.separator + txtDBName.getText(), "facebookapp", "facebookapp");
            EntityManager manager = EntityManagerService.get().createEntityManager();
            
            if (manager != null) {
                facebookanalizapp.FacebookAnalizApp.addDBPropertiesFile(txtDBPath.getText(), txtDBName.getText());
            }
            
            Stage stage = (Stage) txtDBName.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void onCancel(ActionEvent event) {

    }
    
    @FXML
    private void onClickPath(MouseEvent event) {
        DirectoryChooser dic = new DirectoryChooser();
        File file = dic.showDialog(null);
        if (file != null) {
            txtDBPath.setText(file.getPath());
        }
    }
}
