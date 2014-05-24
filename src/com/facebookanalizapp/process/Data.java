
package com.facebookanalizapp.process;

import java.util.List;

/**
 *
 * @author ufuk
 */
public class Data {

    private String name;

    private List<String> jsonDataList;

    public void setData(String dataPath) {

    }

    public List<String> getData() {

        return null;
    }

    public void clearData() {

    }

    public void generateAttributeList() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * @return the jsonDataList
     */
    public List<String> getJsonDataList() {
        return jsonDataList;
    }

    /**
     * @param jsonDataList the jsonDataList to set
     */
    public void setJsonDataList(List<String> jsonDataList) {
        this.jsonDataList = jsonDataList;
    }

}
