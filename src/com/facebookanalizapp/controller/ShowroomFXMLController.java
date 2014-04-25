/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.process.Node;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author hp1
 */
public class ShowroomFXMLController implements Initializable {

    private static ShowroomFXMLController instance = null;

    public static ShowroomFXMLController instance() {
        return instance;
    }
    public Node parentNode;

    @FXML
    TableColumn column;

    @FXML
    TableColumn column2;

    @FXML
    TableColumn column3;

    @FXML
    TableColumn columnId;

    @FXML
    TableColumn columnInfo;

    @FXML
    TableColumn columnState;

    ObservableList<PresentationData> data;
    ObservableList<PresentationData> data2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        column.setCellValueFactory(
                new PropertyValueFactory<PresentationData, String>("name")
        );
        column2.setCellValueFactory(
                new PropertyValueFactory<PresentationData, String>("attribute")
        );

        column3.setCellValueFactory(
                new PropertyValueFactory<PresentationData, String>("index")
        );

        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        columnId.setCellValueFactory(
                new PropertyValueFactory<PresentationData, String>("index")
        );
        columnInfo.setCellValueFactory(
                new PropertyValueFactory<PresentationData, String>("name")
        );

        columnState.setCellValueFactory(
                new PropertyValueFactory<PresentationData, String>("attribute")
        );

        data2 = FXCollections.observableArrayList();
        tableView2.setItems(data2);
    }

    @FXML
    TableView<PresentationData> tableView;

    @FXML
    TableView<PresentationData> tableView2;

    @FXML
    BarChart<String, Integer> barChart;
    
    @FXML
    CategoryAxis catAxis;
    
    @FXML
    NumberAxis numAxis;

    @FXML
    PieChart pieChart;

    @FXML
    Tab tab1;

    @FXML
    Tab tab2;

    @FXML
    Tab tab3;

    @FXML
    TabPane tabPane;

    @FXML
    TitledPane accordion1, accordion2;

    public void fillClusteringTable() {
        tableView.getItems().clear();
        if (parentNode != null && parentNode.getMining() != null) {
            for (int i = 0; i < parentNode.getMining().getClusteringList().size(); i++) {
                String string = parentNode.getMining().getClusteringList().get(i);
                PresentationData item = new PresentationData();
                item.index.setValue(String.valueOf(i + 1));
                item.name.setValue(string);
                item.attribute.setValue(parentNode.getMining().getClusteringAttributeList().get(i));
                data.add(item);
            }
            tableView.setItems(data);
        }

        fillNonClusteringTable();

        accordion1.setText("Clustered "+parentNode.getMining().getClusteringSelectedRulesList().toString());
        accordion2.setText("Non Clustered");
    }

    private void fillNonClusteringTable() {
        tableView2.getItems().clear();
        if (parentNode != null && parentNode.getMining() != null) {
            for (int i = 0; i < parentNode.getMining().getNonClusteringList().size(); i++) {
                String string = parentNode.getMining().getNonClusteringList().get(i);
                PresentationData item = new PresentationData();
                item.index.setValue(String.valueOf(i + 1));
                item.name.setValue(string);
                item.attribute.setValue(parentNode.getMining().getNonclusteringAttributeList().get(i));
                data2.add(item);
            }
            tableView2.setItems(data2);
        }
        
        fillBarChart();
        fillPieChart();
    }

    final static String CLUSTERED = "Clustered";
    final static String NON_CLUSTERED = "Non Clustered";
    final static String CLUSTERING = "Clustering";

    public void fillBarChart() {
        barChart.setTitle("Bar Chart");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(CLUSTERED);       
        series1.getData().add(new XYChart.Data(CLUSTERING, parentNode.getMining().getClusteringList().size()));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(NON_CLUSTERED);
        series2.getData().add(new XYChart.Data(CLUSTERING, parentNode.getMining().getNonClusteringList().size()));
        
        barChart.getData().addAll(series1, series2);
        
        System.out.println("barrrrr");
    }
    
    public void fillPieChart(){
        ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data(CLUSTERED, parentNode.getMining().getClusteringList().size()),
                    new PieChart.Data(NON_CLUSTERED, parentNode.getMining().getNonClusteringList().size()));
        pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data(CLUSTERED +" ( " + (int)pieChartData.get(0).getPieValue()+" )", parentNode.getMining().getClusteringList().size()),
                    new PieChart.Data(NON_CLUSTERED +" ( "+ (int)pieChartData.get(1).getPieValue()+" )", parentNode.getMining().getNonClusteringList().size()));
         
        pieChart.setTitle("Pie Chart");
        pieChart.setData(pieChartData);
        pieChart.setLegendVisible(true);
        pieChart.setClockwise(true);
    }

    public class PresentationData {

        public SimpleStringProperty index = new SimpleStringProperty();

        public SimpleStringProperty name = new SimpleStringProperty();

        public SimpleStringProperty attribute = new SimpleStringProperty();

        public void setName(SimpleStringProperty name) {
            this.name = name;
        }

        public String getName() {
            return name.get();
        }

        public void setAttribute(SimpleStringProperty attribute) {
            this.attribute = attribute;
        }

        public String getAttribute() {
            return attribute.get();
        }

        public void setIndex(SimpleStringProperty index) {
            this.index = index;
        }

        public String getIndex() {
            return index.get();
        }
    }
}
