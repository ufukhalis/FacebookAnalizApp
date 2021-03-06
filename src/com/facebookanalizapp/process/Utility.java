package com.facebookanalizapp.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

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
                List<String> tempList = jr.getPersonLikes(object);
                if (tempList != null) {
                    for (String string : jr.getPersonLikes(object)) {
                        setAttrList.add(string);
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
            Dialogs.create().owner(text).
                    title("Warning").message(string + " field can not be empty!").showWarning();
            return true;
        }
        return false;
    }
}
