/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facebookanalizapp;

import com.facebookanalizapp.ui.MainFrameUI;
import com.facebookanalizapp.ui.NodeUI;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author hp1
 */
public class FacebookAnalizApp extends Application {
    
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final String ApplicationTitle ="Facebook Analiz Uygulaması";
      
    
    @Override
    public void start(Stage primaryStage) {
 
        MainFrameUI root = new MainFrameUI();

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        primaryStage.setTitle(ApplicationTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
