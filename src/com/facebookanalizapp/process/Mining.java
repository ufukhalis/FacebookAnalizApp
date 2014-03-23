/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.process;

import java.util.List;

/**
 *
 * @author ufuk
 */
public class Mining {
    private String name;
    
    private Integer mininType;
    
    private String cosineArray;
    
    private List<String> presentationData;

    
    public void calculateCosine(Data data){
        
    }
    
    public void generateDataMining(){
        
    }
    
    private void generateKmeans(Integer k, String cosineArray){
        
    }
    
    private void generateDBScan(Double i, Double j, Double k, String cosineArray){
        
    }
    
    private void generateClustering(String ruleList, Data jsonData){
        
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
     * @return the mininType
     */
    public Integer getMininType() {
        return mininType;
    }

    /**
     * @param mininType the mininType to set
     */
    public void setMininType(Integer mininType) {
        this.mininType = mininType;
    }

    /**
     * @return the cosineArray
     */
    public String getCosineArray() {
        return cosineArray;
    }

    /**
     * @param cosineArray the cosineArray to set
     */
    public void setCosineArray(String cosineArray) {
        this.cosineArray = cosineArray;
    }

    /**
     * @return the presentationData
     */
    public List<String> getPresentationData() {
        return presentationData;
    }

    /**
     * @param presentationData the presentationData to set
     */
    public void setPresentationData(List<String> presentationData) {
        this.presentationData = presentationData;
    }
    
    
}
