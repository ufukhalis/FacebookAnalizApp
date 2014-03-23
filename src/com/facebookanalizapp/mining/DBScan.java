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
public class DBScan {
    public List<String> returnList;
    
    public static DBScan dbScan = null;

    private DBScan() {
    }
    
    public static synchronized DBScan instance(){
        if (dbScan == null) {
            dbScan = new DBScan();
        }
        return dbScan;
    }
    
    private void generateDBScan(Double i, Double j, Double k, String cosineArray){
        
    }
}
