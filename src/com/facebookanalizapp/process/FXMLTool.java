/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.process;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ufuk
 */
public class FXMLTool {

    private static final String FXML_LOCATION = "/com/facebookanalizapp/fxml/";

    private static FXMLTool fXMLTool = null;

    public synchronized static FXMLTool instance() {
        if (fXMLTool == null) {
            return fXMLTool = new FXMLTool();
        }
        return fXMLTool;
    }

    public void openFXML(String title, String fxmlFileName, boolean isResizable) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(FXML_LOCATION + fxmlFileName));
            Scene secondScene = new Scene(parent);
            Stage secondStage = new Stage();
            secondStage.setTitle(title);
            secondStage.setScene(secondScene);
            secondStage.setResizable(isResizable);
            secondStage.initModality(Modality.APPLICATION_MODAL);

            secondStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openFXML(String title, String fxmlFileName, boolean isResizable, int width, int height) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(FXML_LOCATION + fxmlFileName));
            Scene secondScene = new Scene(parent);
            Stage secondStage = new Stage();
            secondStage.setTitle(title);
            secondStage.setScene(secondScene);
            secondStage.setWidth(width);
            secondStage.setHeight(height);
            secondStage.setResizable(isResizable);
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <T> T getController(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LOCATION + fxml));       
            loader.load();
            return (T)loader.getController();
        } catch (Exception ex) {
            Logger.getLogger(FXMLTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public FXMLLoader getLoader(String fxml){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LOCATION + fxml));
            loader.load();            
            return loader;
        } catch (Exception ex) {
            Logger.getLogger(FXMLTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

}
