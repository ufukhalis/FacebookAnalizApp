/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
