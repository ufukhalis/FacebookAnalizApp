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
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public <T> String listToString(List<T> list, String delimeter) {
        String str = "";
        for (T t : list) {
            str = str + t + delimeter;
        }
        return str;
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

    public Boolean showWarningDialogIfTextEmpty(Stage s, TextField text, String string) {
        if (text.getText().isEmpty() || text.getText().equalsIgnoreCase("")) {
            Dialogs.showWarningDialog((Stage) s, string + " alanı boş bırakılamaz!",
                    "Dikkat", "Uyarı");
            return true;
        }
        return false;
    }
}
