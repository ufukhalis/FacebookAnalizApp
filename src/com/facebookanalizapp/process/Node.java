
package com.facebookanalizapp.process;

import com.facebookanalizapp.controller.ShowroomFXMLController;
import com.facebookanalizapp.ui.NodeUI;

/**
 *
 * @author ufuk halis
 */
public class Node {
    private String name;
    private Data data;
    private Mining mining;
    private Presentation presentation;
    private NodeUI ndUi;

    
    public Node(String name, int x, int y) {
        this.name = name;
        this.ndUi = new NodeUI(this,x, y);
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public void addData(String databaseName){
        
    }
    
    public void addPresentation(String presentationName){
        
    }
    
    public void addMining(String miningName){
        
    }
    
    public void removeData(){
        
    }
    
    public void removePresentation(){
        
    }
    
    public void removeMining(){
        
    }
    
    public void execute(Node node){
        if (node.mining != null) {
            switch(node.mining.getMininType()){
                case 1://clustering
                    node.mining.generateClustering(data);
                    FXMLTool.instance().openFXML("ShowRoom", "ShowroomFXML.fxml", true);
                    ShowroomFXMLController.instance().parentNode = node;
                    ShowroomFXMLController.instance().getShowRoom();
                    break;
                case 2://kmeans
                    FXMLTool.instance().openFXML("ShowRoom", "ShowroomFXML.fxml", true);
                    ShowroomFXMLController.instance().parentNode = node;
                    ShowroomFXMLController.instance().getShowRoom();
                    break;
                case 3://dbscan
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @return the ndUi
     */
    public NodeUI getNdUi() {
        return ndUi;
    }

    /**
     * @return the data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * @return the mining
     */
    public Mining getMining() {
        return mining;
    }

    /**
     * @param mining the mining to set
     */
    public void setMining(Mining mining) {
        this.mining = mining;
    }

    /**
     * @return the presentation
     */
    public Presentation getPresentation() {
        return presentation;
    }

    /**
     * @param presentation the presentation to set
     */
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    
}
