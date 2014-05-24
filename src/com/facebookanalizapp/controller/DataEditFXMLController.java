
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.JsonReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp1
 */
public class DataEditFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData() {
        //Seçili olan veri gösterilir
        String value = DataFXMLController.instance().viewData.getSelectionModel().getSelectedItem().data.getValue();
        txtEdit.setText(value);

    }

    @FXML
    TextArea txtEdit;

    @FXML
    private void onSave(ActionEvent event) {

        JsonReader jr = new JsonReader();
        if (jr.isValid(txtEdit.getText())) {
            DataFXMLController.instance().viewData.getSelectionModel().getSelectedItem().data.setValue(txtEdit.getText());
            //Refresh
            DataFXMLController.instance().viewData.getColumns().get(0).setVisible(false);
            DataFXMLController.instance().viewData.getColumns().get(0).setVisible(true);
            onCancel(event);
        }else{
             Dialogs.showWarningDialog(null, 
                    "Invalid Json format. Please check your edit.","Invalid Json Format!", "Warning");
        }
        
    }

    @FXML
    Button btnCancel;

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
