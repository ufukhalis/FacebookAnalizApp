/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.process;

import com.facebookanalizapp.chart.Chart;
import java.util.List;

/**
 *
 * @author ufuk
 */
public class Presentation {
    private String name;
    
    private Integer chartType;
    
    private Chart chart;

    
    public void generatePresentation(List<String> presentationData){
        
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
     * @return the chartType
     */
    public Integer getChartType() {
        return chartType;
    }

    /**
     * @param chartType the chartType to set
     */
    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    /**
     * @return the chart
     */
    public Chart getChart() {
        return chart;
    }

    /**
     * @param chart the chart to set
     */
    public void setChart(Chart chart) {
        this.chart = chart;
    }
    
    
}
