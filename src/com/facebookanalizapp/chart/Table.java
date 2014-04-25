/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.chart;

import java.util.List;
import javafx.scene.control.TableView;

/**
 *
 * @author ufuk
 */
public class Table implements Chart{

    @Override
    public void drawChart(List<String> presentationData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private TableView tableView = new TableView();
    
    static final String columnsName[] = {
        "Name",
        "Email",
        "Attributes"};
    
    
}
