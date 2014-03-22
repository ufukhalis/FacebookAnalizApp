/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookanalizapp;

import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.ui.MainFrameUI;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hp1
 */
public class FacebookAnalizApp extends Application {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final String ApplicationTitle = "Facebook Analiz Uygulaması";

    @Override
    public void start(Stage primaryStage) throws IOException {

        /*MainFrameUI root = new MainFrameUI();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle(ApplicationTitle);
        primaryStage.setScene(scene);
        primaryStage.show();*/

        
        //URL location = getClass().getResource("/com/facebookanalizapp/fxml/DataFXML.fxml");  
        //System.out.println("value : " + location);
        
        FXMLTool.instance().openFXML("Veri İşlemleri", "MainFXML.fxml", true);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
