/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.mining;

import java.util.List;

/**
 *
 * @author ufuk
 */
public class KMeans {
    public List<String> returnList;
    
    public static KMeans kmeans = null;

    private KMeans() {
    }
    
    public static synchronized KMeans instance(){
        if (kmeans == null) {
            kmeans = new KMeans();
        }
        return kmeans;
    }
    
    public void calculateKMeans(Integer k, String cosineArray){
        
    }
}
