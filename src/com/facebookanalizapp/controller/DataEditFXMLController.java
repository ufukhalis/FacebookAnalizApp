/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.JsonFormatter;
import com.facebookanalizapp.process.JsonReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.json.JSONObject;

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
                    "Json format yapısında düzenlemeler sırasında bozulma tespit edilmiştir.Bundan dolayı kayıt edilememektedir.","Json formatında bozulma!", "Uyarı");
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
