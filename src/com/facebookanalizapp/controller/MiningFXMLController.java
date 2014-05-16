/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.controller;

import com.facebookanalizapp.mining.KMeans;
import com.facebookanalizapp.process.JsonReader;
import com.facebookanalizapp.process.Mining;
import com.facebookanalizapp.process.Node;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.SwingWorker;

/**
 * FXML Controller class
 *
 * @author ufuk
 */
public class MiningFXMLController implements Initializable {

    public Node parentNode;

    private static MiningFXMLController instance = null;

    public static MiningFXMLController instance() {
        return instance;
    }

    @FXML
    Button btnKmeansSelect;
    @FXML
    Button btnScanSelect;
    @FXML
    Button btnClustSelect;
    @FXML
    Button btnSaveClustering;
    @FXML
    Button btnGetSelectedClustering;
    @FXML
    Button btnRemove;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtClusteringName;
    @FXML
    ListView lstViewClusteringDB;
    @FXML
    ListView lstViewSelectedAttr;
    @FXML
    ListView lstViewAttrDB;
    
    @FXML
    TextField KM_NAME;
    @FXML
    TextField KM_K;
    @FXML
    TextField KM_LOOP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }

    public List<String> tempAttributeList = null;

    public void fillAttributeList() {
        //lstViewAttrDB
        Set<String> setAttrList = new HashSet<>();
        try {

            JsonReader jr = new JsonReader();
            for (String object : parentNode.getData().getJsonDataList()) {
                //System.out.println("value : " + object);
                List<String> tempList = jr.getPersonLikes(object);
                if (tempList != null) {
                    for (String string : jr.getPersonLikes(object)) {
                        setAttrList.add(string);
                        //System.out.println("value : " + string);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Ex 1: " + e);
        }

        //Attribute sırası önemli olduğundan böyle bir işlem yapılıyor. Mining bölümü ilk açıldığında dizi doldurulur.
        if (tempAttributeList == null) {
            tempAttributeList = new ArrayList<>();
            for (Iterator<String> it = setAttrList.iterator(); it.hasNext();) {
                String string = it.next();
                tempAttributeList.add(string);
            }
        }
        ObservableList<String> items = FXCollections.observableArrayList(setAttrList);
        lstViewAttrDB.setItems(items);

        //Seçilen bir liste var ise seçilenler listesini doldur.
        if (parentNode.getMining() != null && parentNode.getMining().getMininType() == 1) {

            Mining mng = parentNode.getMining();
            for (String selected : mng.getClusteringSelectedRulesList()) {
                lstViewAttrDB.getItems().remove(selected);
                lstViewSelectedAttr.getItems().add(selected);
            }
            lstViewAttrDB.getSelectionModel().clearSelection();
        }

    }
    
    public void fillKmeansControls(){
        if (parentNode.getMining() != null && parentNode.getMining().getMininType() == 2) {
            Mining m = parentNode.getMining();
            KM_NAME.setText(m.getName());
            KM_K.setText(m.getK().toString());
            KM_LOOP.setText(m.getLoop().toString());
        }
    }

    /**
     * **************************************************
     */
    //Kmeans started
    @FXML
    private void onKMeansSelect(ActionEvent event) {

        Mining mining = new Mining();
        mining.setMininType(2);
        mining.setName(KM_NAME.getText());//Dışardan alınacak şimdilik böyle
        mining.setK(Integer.parseInt(KM_K.getText()));
        mining.setLoop(Integer.parseInt(KM_LOOP.getText()));
        parentNode.setMining(mining);
        String[][] attributeArray = null; //= createAttributeArray(parentNode.getData().getJsonDataList().size(), tempAttributeList.size());

        ProgressBar updProg = new ProgressBar(0);
        updProg.setProgress(0F);
        StackPane layout = new StackPane();
        layout.getChildren().add(updProg);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(layout));
        stage.setTitle("İşleniyor...");
        stage.show();

        CreateAttributeArray cr = new CreateAttributeArray(attributeArray, parentNode.getData().getJsonDataList().size(), tempAttributeList.size(), parentNode, tempAttributeList);
        cr.setK(mining.getK());
        cr.setLoop(mining.getLoop());
        cr.setP(updProg);
        cr.execute();

        
        parentNode.getNdUi().getBranch2().getLblInfo().textProperty().setValue("K-Means seçildi");
        
        closeWindow();
    }

    private void calculateKmeans(String[][] array) {

    }

    /**
     * **************************************************
     */
    //Kmeans ended
    @FXML
    private void onScanSelect(ActionEvent event) {

    }

    @FXML
    private void onClustSelect(ActionEvent event) {
        Mining mining = new Mining();
        mining.setCosineArray("");
        mining.setMininType(1);
        mining.setPresentationData(null);
        mining.setName(txtClusteringName.getText());
        for (Iterator it = lstViewSelectedAttr.getItems().iterator(); it.hasNext();) {
            String str = it.next().toString();
            mining.setClusteringSelectedRulesList(str);
        }

        parentNode.setMining(mining);
        parentNode.getNdUi().getBranch2().getLblInfo().textProperty().setValue("Clustering seçildi");
        closeWindow();
    }

    @FXML
    private void onGetSelectedClustering(ActionEvent event) {

    }

    @FXML
    private void onRemove(ActionEvent event) {
        addListToList(lstViewSelectedAttr, lstViewAttrDB);
    }

    @FXML
    private void onAdd(ActionEvent event) {
        addListToList(lstViewAttrDB, lstViewSelectedAttr);
    }

    @FXML
    private void onSaveClustering(ActionEvent event) {

    }

    private void addListToList(ListView from, ListView to) {
        try {
            String selected = from.getSelectionModel().getSelectedItem().toString();
            from.getItems().remove(selected);
            to.getItems().add(selected);
            from.getSelectionModel().clearSelection();
        } catch (Exception e) {
            Dialogs.showErrorDialog(null, "Bir seçim yapmadınız!!");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) lstViewAttrDB.getScene().getWindow();
        stage.close();
    }
}

class CreateAttributeArray extends SwingWorker<String[][], Integer> {

    String[][] attributeArray;
    int personCount;
    int attributeCount;
    Node parentNode;
    List<String> tempAttributeList;
    int k;
    int loop;
    ProgressBar p;

    public void setP(ProgressBar p) {
        this.p = p;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }
    
    

    public CreateAttributeArray(String[][] array, int personCount, int attributeCount, Node parentNode, List<String> list) {
        array = new String[personCount][attributeCount];
        this.attributeArray = array;
        this.personCount = personCount;
        this.attributeCount = attributeCount;
        this.parentNode = parentNode;
        this.tempAttributeList = list;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    protected String[][] doInBackground() throws Exception {
        JsonReader jr = new JsonReader();
        for (int i = 0; i < personCount; i++) {
            for (int j = 0; j < attributeCount; j++) {
                String first = parentNode.getData().getJsonDataList().get(i);
                List<String> likes = jr.getPersonLikes(first);
                int count = 0;
                if (likes != null) {
                    for (int m = 0; m < likes.size(); m++) {
                        if (likes.get(m).equalsIgnoreCase(tempAttributeList.get(j))) {
                            count++;
                        }
                    }
                    attributeArray[i][j] = String.valueOf(count);
                } else {
                    Arrays.fill(attributeArray[i], "0");
                }
            }
            this.publish(((int) ((Double.valueOf(i) / Double.valueOf(personCount)) * Double.valueOf(100))));
        }
        return attributeArray;
    }

    @Override
    protected void done() {

        JsonReader jr = new JsonReader();

        //first loop start
        List<String[]> centers = new ArrayList<>();
        int[] randomClass = selectRandomClass(k, personCount);
        for (int i : randomClass) {
            centers.add(attributeArray[i]);
        }
        List<KMeans> kmeansList = new ArrayList<>();
        for (int i = 0; i < personCount; i++) {
            List<Double> result = new ArrayList<>();

            if (isArrayInList(centers, attributeArray[i]) == false) {
                for (String[] strings : centers) {
                    result.add(calculateCosine(strings, attributeArray[i]));
                }
                if (result.size() > 0) {
                    int index = result.indexOf((double) Collections.max(result));
                    System.out.println("Result : " + result.toString());
                    KMeans k = new KMeans();
                    k.setKmeansName("C" + index);
                    k.setPersonName(jr.getPersonName(parentNode.getData().getJsonDataList().get(i)));
                    kmeansList.add(k);
                }
            } else {
                int index = 0;
                for (int j = 0; j < centers.size(); j++) {
                    if (Arrays.equals(attributeArray[i], centers.get(j))) {
                        index = j;
                        break;
                    }
                }

                KMeans k = new KMeans();
                k.setKmeansName("C" + index);
                k.setPersonName(jr.getPersonName(parentNode.getData().getJsonDataList().get(i)));
                kmeansList.add(k);
            }
        }
        //first loop end
        System.out.println("Kmeans size : " + kmeansList.size());
        //new center started
        List<Double> newCenterValues = new ArrayList<>();
        centers = new ArrayList<>();
        setNewCenters(kmeansList, newCenterValues, centers);
        //new center ended
        kmeansList = new ArrayList<>();
        for (int i = 0; i < loop; i++) {
            for (int l = 0; l < personCount; l++) {
                List<Double> result = new ArrayList<>();
                for (int j = 0; j < centers.size(); j++) {
                    result.add(calculateCosine(centers.get(j), attributeArray[l]));
                }
                if (result.size() > 0) {
                    int index = result.indexOf((double) Collections.max(result));
                    System.out.println("Result : " + result.toString());
                    KMeans k = new KMeans();
                    k.setKmeansName("C" + index);
                    k.setPersonName(jr.getPersonName(parentNode.getData().getJsonDataList().get(l)));
                    kmeansList.add(k);
                }
            }
            System.out.println("Kmeans size : " + kmeansList.size());
            centers = new ArrayList<>();
            newCenterValues = new ArrayList<>();
            setNewCenters(kmeansList, newCenterValues, centers);
            if (i != loop - 1) {
                kmeansList = new ArrayList<>();
            }
        }
        p.setProgress(1F);
        parentNode.getMining().setKmeansPresentationData(kmeansList);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (p.getProgress() == 1F) {
                    Stage stage1 = (Stage) p.getScene().getWindow();
                    stage1.close();
                }
            }
        });

    }

    private void setNewCenters(List<KMeans> kmeansList, List<Double> newCenterValues, List<String[]> centers) {

        for (int i = 0; i < k; i++) {
            double result = 0;
            int size = 0;
            for (int m = 0; m < attributeCount; m++) {
                for (int j = 0; j < kmeansList.size(); j++) {
                    if (kmeansList.get(j).getKmeansName().equalsIgnoreCase("C" + i)) {
                        result += Double.valueOf(attributeArray[j][m]);
                        size++;
                    }
                }
                newCenterValues.add((double) (result / size));

            }
            if (newCenterValues.size() > 0) {
                centers.add(createCenter(newCenterValues));
                System.out.println("New Centers : " + newCenterValues.toString());
                newCenterValues = new ArrayList<>();
            }
        }
    }

    private String[] createCenter(List<Double> list) {
        String[] center = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            center[i] = String.valueOf(list.get(i));
        }

        return center;
    }

    @Override
    protected void process(List<Integer> chunks) {
        //System.out.println("Value : " + chunks.get(chunks.size() - 1));

        Float f = Float.valueOf((Float.valueOf((chunks.get(chunks.size() - 1)) / 100F)));
        System.out.println("Value : " + f);
        p.setProgress(f);
    }

    private int[] selectRandomClass(int k, int limit) {
        List<Integer> lst = new ArrayList<>();

        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < k) {
            Integer next = rng.nextInt(limit);
            generated.add(next);
        }

        for (Iterator<Integer> it = generated.iterator(); it.hasNext();) {
            lst.add(it.next());
        }

        int[] arr = new int[k];
        for (int i = 0; i < lst.size(); i++) {
            arr[i] = lst.get(i);
            System.out.println("Center : " + arr[i]);

        }

        return arr;
    }

    private double calculateCosine(String[] centroid, String[] to) {
        double x = 0;
        double c = 0;
        double t = 0;
        for (int i = 0; i < centroid.length; i++) {
            x = x + (Double.parseDouble(centroid[i]) * Double.parseDouble(to[i]));
            c = c + (Double.parseDouble(centroid[i]) * Double.parseDouble(centroid[i]));
            t = t + (Double.parseDouble(to[i]) * Double.parseDouble(to[i]));
        }

        c = Math.sqrt(c);
        t = Math.sqrt(t);

        if (t == 0 || c == 0) {
            return 0;
        }

        return x / (t * c);
    }

    private boolean isArrayInList(List<String[]> list, String[] array) {
        for (String[] strings : list) {
            if (Arrays.equals(array, strings)) {
                return true;
            }
        }
        return false;
    }
}
