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

    public MongoRecovery(ArrayList<OpLog> opLogsToRemove) {
        this.opLogsToRemove = opLogsToRemove;
    }

    public ArrayList<OpLog> getOpLogsToRemove() {
        return opLogsToRemove;
    }

    public void setOpLogsToRemove(ArrayList<OpLog> opLogsToRemove) {
        this.opLogsToRemove = opLogsToRemove;
    }
    
    
    public abstract void recover();
    
    
}
