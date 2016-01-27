/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import java.util.ArrayList;

/**
 *
 * @author davidmatos
 */
public abstract class MongoRecovery {
    
    private ArrayList<OpLog> opLogsToRemove;
    private String database;
    

    public MongoRecovery(ArrayList<OpLog> opLogsToRemove, String database) {
        this.opLogsToRemove = opLogsToRemove;
        this.database = database;
    }

    public ArrayList<OpLog> getOpLogsToRemove() {
        return opLogsToRemove;
    }

    public void setOpLogsToRemove(ArrayList<OpLog> opLogsToRemove) {
        this.opLogsToRemove = opLogsToRemove;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    
    
    public abstract void recover();

 
    
    
}
