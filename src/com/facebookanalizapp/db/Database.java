

package com.facebookanalizapp.db;

import com.facebookanalizapp.process.Node;
import java.util.List;

/**
 *
 * @author ufuk halis
 */
public class Database {
    private String name;
    
    private String path;
    
    private List<Node> nodeList;

    
    public void export(String path){
        
    }
    
    public void delete(){
        
    }
    
    public Integer getNodeCount(){
        
        return 0;
    }
    
    public void addNode(String name){
        
    }
    
    public void removeNode(String name){
        
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the nodeList
     */
    public List<Node> getNodeList() {
        return nodeList;
    }

    /**
     * @param nodeList the nodeList to set
     */
    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
    
    
}
