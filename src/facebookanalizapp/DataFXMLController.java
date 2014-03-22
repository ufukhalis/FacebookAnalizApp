/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookanalizapp;

import com.facebookanalizapp.process.ExcelReader;
import com.facebookanalizapp.process.JsonReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp1
 */
public class DataFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        column.setCellValueFactory(
                new PropertyValueFactory<JSonData, String>("data")
        );

        data = FXCollections.observableArrayList();
        viewData.setItems(data);
    }

    ObservableList<JSonData> data;

    @FXML
    TableView<JSonData> viewData;

    @FXML
    TableColumn column;

    @FXML
    TextField txtPath;

    @FXML
    private void onBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Seç");
        File file = fileChooser.showOpenDialog(null);

        txtPath.setText(file.getPath());

        ExcelReader reader = new ExcelReader();
        List<String> list = reader.read(txtPath.getText().trim());

        for (String string : list) {
            if (JsonReader.isValid(string)) {
                JSonData item = new JSonData();
                item.data.setValue(string);
                data.add(item);
            }
        }
    }

    @FXML
    private void onEdit(ActionEvent event) {
        String data = viewData.getSelectionModel().getSelectedItem().data.getValue();
        System.out.println("value : " + viewData.getSelectionModel().getSelectedItem().data.getValue());

        Parent dataroot = null;
        FXMLLoader fxmlLoader = null;
       
        fxmlLoader = new FXMLLoader(getClass().getResource("DataEditFXML.fxml")); 
        try {
            dataroot = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(DataFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataEditFXMLController controller
                = fxmlLoader.<DataEditFXMLController>getController();
        
        controller.initData(data);

        Scene secondScene = new Scene(dataroot);
        Stage secondStage = new Stage();
        secondStage.setTitle("Veri Düzenle");
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);

        secondStage.show();
    }

    @FXML
    private void onSave(ActionEvent event) {

    }

    public class JSonData {

        public SimpleStringProperty data = new SimpleStringProperty();

        public void setData(SimpleStringProperty dataString) {
            this.data = dataString;
        }

        public String getData() {
            return data.get();
        }

    }
}
