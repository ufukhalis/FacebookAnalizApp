
package com.facebookanalizapp.process;

/**
 *
 * @author hp1
 */
public class DBProperty {
    private String dbName;
    private String dbPath;

    public DBProperty(String dbName, String dbPath) {
        this.dbName = dbName;
        this.dbPath = dbPath;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getDbPath() {
        return dbPath;
    }
    
    
}
