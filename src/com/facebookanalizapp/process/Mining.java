/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.process;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ufuk
 */
public class Mining {

    private String name;

    private Integer mininType;

    private String cosineArray;

    private List<String> presentationData;

    private List<String> clusteringSelectedRulesList = new ArrayList<String>();
    
    private List<String> clusteringList = new ArrayList<String>();
    private List<String> nonClusteringList = new ArrayList<String>();

    public void setClusteringSelectedRulesList(String rule) {
        this.clusteringSelectedRulesList.add(rule);
    }

    public List<String> getClusteringSelectedRulesList() {
        return clusteringSelectedRulesList;
    }

    public void calculateCosine(Data data) {

    }

    public void generateDataMining() {

    }

    public void generateKmeans(Integer k, String cosineArray) {

    }

    public void generateDBScan(Double i, Double j, Double k, String cosineArray) {

    }

    public void generateClustering(Data jsonData) {

        Set<String> setAttrList = new HashSet<>();
        try {
            int size = 0;
            JsonReader jr = new JsonReader();
            for (String object : jsonData.getJsonDataList()) { //tablo json listesi
                List<String> tempList = jr.getPersonLikes(object);//gelen kişinin like larını bul
                if (tempList != null) {
                    for (String string : tempList) {
                        setAttrList.add(string);                        
                    }
                    for (String string : clusteringSelectedRulesList) { //seçili atrribute listesi
                        for (Iterator<String> it = setAttrList.iterator(); it.hasNext();) {
                            String attribute = it.next();
                            
                            if (attribute.contains(string)) {
                                //System.out.println(attribute + " == " + string);
                                
                                size++;
                            }

                        }
                        if (size == clusteringSelectedRulesList.size()) {
                            size = 0;
                            //System.out.println("Name : " + jr.getPersonName(object));
                            clusteringList.add(jr.getPersonName(object));
                            //System.out.println("Attribute : " + setAttrList +" \n");                            
                        }else{
                            nonClusteringList.add(jr.getPersonName(object));
                        }
                    }
                    setAttrList = new HashSet<>();
                }

            }

        } catch (Exception e) {
            System.out.println("Ex : " + e);
        }

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
