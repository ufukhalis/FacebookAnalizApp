
package facebookanalizapp;

import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.PropertyManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author hp1
 */
public class FacebookAnalizApp extends Application {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final String ApplicationTitle = "Facebook Analysis Application";

    @Override
    public void start(Stage primaryStage) throws IOException {
        PropertyManager.instance().createOrReadPropertiesFile();        
        FXMLTool.instance().openFXML(ApplicationTitle, "MainFXML.fxml", true);
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
