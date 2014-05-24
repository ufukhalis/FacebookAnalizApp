/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.mining;

import com.facebookanalizapp.process.Data;
import java.util.List;

/**
 *
 * @author ufuk
 */
public class Clustering {
    public List<String> returnList;
    
    public static Clustering clustering = null;

    private Clustering() {
    }
    
    public static synchronized Clustering instance(){
        if (clustering == null) {
            clustering = new Clustering();
        }
        return clustering;
    }
    
}
