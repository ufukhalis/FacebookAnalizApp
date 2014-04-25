/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.process;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Ufuk
 * 
 */
public class JsonReader {
    
    
    /**
     * 
     * @param personData Single person json data
     * @return This method returns a person likes category lists
     */
    public List<String> getPersonLikes(String personData){
        List<String> data = new ArrayList<>();
        try {
            if (personData.contains("likes") && isValid(personData)) {
                JSONObject json = new JSONObject(personData);
                String likesJson = json.get("likes").toString();
                json = new JSONObject(likesJson);
                JSONArray jarray = json.getJSONArray("data");
                for(int i = 0; i < jarray.length(); i++){
                    JSONObject oneLike = jarray.getJSONObject(i);
                    data.add(oneLike.get("category").toString());
                }
                return data;
            }
        } catch (Exception e) {
            System.out.println("Error : Json data cannot read cause : " +e);
        }
        return null;
    }
    
    
    public String getPersonName(String personData){
        try {
            if (personData.contains("name") && personData.contains("email")) {
                JSONObject json = new JSONObject(personData);
                String nameJson = json.get("name").toString();
                String emailJson = json.get("email").toString();
                               
                return nameJson + ";" + emailJson;
            }
        } catch (Exception e) {
            System.out.println("Error : Json data cannot read cause : " +e);
        }
        return null;
    }
    
    /**
     * 
     * @param json A json string
     * @return This method returns json string is valid or invalid
     */
    public static boolean isValid(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (Exception jse) {
            return false;
        }
    }
}
