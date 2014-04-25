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

    private List<String> clusteringSelectedRulesList = new ArrayList<>();

    private List<String> clusteringList = new ArrayList<>();//Sınıflandırılmış Liste : Name;Email
    private List<String> clusteringAttributeList = new ArrayList<>(); // Sınıflandırılmış kişilerin attribute listesi
    
    private List<String> nonClusteringList = new ArrayList<>();//Sınıflandırılmamış Liste : Name;Email
    private List<String> nonclusteringAttributeList = new ArrayList<>();// Sınıflandırılmamış kişilerin attribute listesi

    public List<String> getClusteringAttributeList() {
        return clusteringAttributeList;
    }

    public List<String> getNonclusteringAttributeList() {
        return nonclusteringAttributeList;
    }

         
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
        clusteringList = new ArrayList<>();
        nonClusteringList = new ArrayList<>();
        Set<String> setAttrList = new HashSet<>();
        try {

            JsonReader jr = new JsonReader();
            for (String object : jsonData.getJsonDataList()) { //tablo json listesi
                List<String> tempList = jr.getPersonLikes(object);//gelen kişinin like larını bul
                if (tempList != null) {
                    for (String string : tempList) {
                        setAttrList.add(string);
                    }
                    int size = 0;
                    for (String string : clusteringSelectedRulesList) { //seçili atrribute listesi
                        if (setAttrList.contains((String) string)) {
                            size++;
                        }
                        
                    }
                    if (size == clusteringSelectedRulesList.size()) {

                        //System.out.println("Name : " + jr.getPersonName(object));
                        getClusteringList().add(jr.getPersonName(object));
                        clusteringAttributeList.add(setAttrList.toString());
                        //System.out.println("Attribute : " + setAttrList + " \n");
                    } else {
                        getNonClusteringList().add(jr.getPersonName(object));
                        nonclusteringAttributeList.add(setAttrList.toString());
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

    /**
     * @return the clusteringList
     */
    public List<String> getClusteringList() {
        return clusteringList;
    }

    /**
     * @param clusteringList the clusteringList to set
     */
    public void setClusteringList(List<String> clusteringList) {
        this.clusteringList = clusteringList;
    }

    /**
     * @return the nonClusteringList
     */
    public List<String> getNonClusteringList() {
        return nonClusteringList;
    }

    /**
     * @param nonClusteringList the nonClusteringList to set
     */
    public void setNonClusteringList(List<String> nonClusteringList) {
        this.nonClusteringList = nonClusteringList;
    }

}
