package com.facebookanalizapp.controller;

import com.facebookanalizapp.entitymanager.EntityManagerService;
import com.facebookanalizapp.process.DBProperty;
import com.facebookanalizapp.process.PropertyManager;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import org.controlsfx.dialog.Dialogs;

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
        EntityManagerService.clearDB();
        if (!txtDBName.getText().isEmpty() && !txtDBPath.getText().isEmpty() && !isDBExists(txtDBName.getText())) {
            EntityManagerService.setPersistenceMap(txtDBPath.getText() + File.separator + txtDBName.getText(), "facebookapp", "facebookapp");
            EntityManager manager = EntityManagerService.get().createEntityManager();

            if (manager != null) {
                PropertyManager.instance().addDBPropertiesFile(txtDBPath.getText(), txtDBName.getText());

                Stage stage = (Stage) txtDBName.getScene().getWindow();
                stage.close();
                MainFXMLController.instance().refreshDatabasesList(false); //Bu sorun çıkartıyor!!
                Dialogs.create().owner(txtDBName).
                        title("Info").message("Database was created :  " + txtDBName.getText()).showInformation();
                
            } else {
                Dialogs.create().owner(txtDBName).
                        title("Error").message("Database was not created!!").showError();
               
            }
        } else {
            Dialogs.create().owner(txtDBName).title("Please check details").message("Empty spaces should not be!.\nThe database name can not be same!").showWarning();

        }
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) txtDBName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onClickPath(MouseEvent event) {
        DirectoryChooser dic = new DirectoryChooser();
        File file = dic.showDialog(null);
        if (file != null) {
            txtDBPath.setText(file.getPath());
        }
    }

    private Boolean isDBExists(String dbName) {
        List<DBProperty> list = PropertyManager.instance().getAllDatabasesFromPropertyFile();
        for (DBProperty dBProperty : list) {
            if (dbName.equalsIgnoreCase(dBProperty.getDbName())) {
                return true;
            }
        }
        return false;
    }
}
