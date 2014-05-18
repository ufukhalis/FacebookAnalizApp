/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author hp1
 */
public class Utility {

    private static Utility util = null;

    public static synchronized Utility instance() {
        if (util == null) {
            util = new Utility();
        }
        return util;
    }

    private Utility() {
    }

    public List<String> getListFromRaw(String raw, String delimeter) {
        System.out.println("raw : " + raw);
        String[] list = raw.split(delimeter);
        return Arrays.asList(list);
    }

    public List<String> getAttributeList(Node parentNode) {
        Set<String> setAttrList = new HashSet<>();
        try {

            JsonReader jr = new JsonReader();
            for (String object : parentNode.getData().getJsonDataList()) {
                //System.out.println("value : " + object);
                List<String> tempList = jr.getPersonLikes(object);
                if (tempList != null) {
                    for (String string : jr.getPersonLikes(object)) {
                        setAttrList.add(string);
                        //System.out.println("value : " + string);
                    }
                }

            }

            List<String> lst = new ArrayList<>();
            for (Iterator<String> it = setAttrList.iterator(); it.hasNext();) {
                String string = it.next();
                lst.add(string);
            }
            return lst;
        } catch (Exception e) {
            System.out.println("Ex 1: " + e);
        }

        return null;
    }
}
