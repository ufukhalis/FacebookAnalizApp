/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookanalizapp;

import com.facebookanalizapp.controller.DataFXMLController;
import com.facebookanalizapp.entity.DataEntity;
import com.facebookanalizapp.entitymanager.EntityManagerService;
import com.facebookanalizapp.process.ExcelReader;
import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.JsonReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
        ExcelReader reader = new ExcelReader();
        List<String> list = reader.read("E:/users2.xlsx");
        String veri = "";
        for (String string : list) {
            if (JsonReader.isValid(string)) {
                System.out.println("Value : " + string);
            }
        }
        EntityManagerService.setPersistenceMap("c:/ufuk/db", "ufuk", "ufuk");
        EntityManager manager = EntityManagerService.get().createEntityManager();
        
        
        manager.getTransaction().begin();
        
        /*DataEntity data = new DataEntity();
        data.setName("test");
        data.setRawData(veri);
        
        manager.persist(data);
        manager.getTransaction().commit();
        */
        
        Query q = manager.createQuery("select a from DataEntity a");
        List<DataEntity> lstAd = q.getResultList();
        /*for (DataEntity ad : lstAd) {
          System.out.println("*********************************");
          System.out.println(ad.getRawData());
            
        }*/
        /*String[] s = lstAd.get(0).getRawData().split("#");
        for (String string : s) {
            System.out.println("value : " + string);
        }*/
        manager.close();
        
        FXMLTool.instance().openFXML(ApplicationTitle, "MainFXML.fxml", true);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
