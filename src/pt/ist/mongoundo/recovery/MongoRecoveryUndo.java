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
public class MongoRecoveryUndo extends MongoRecovery{
    
    public MongoRecoveryUndo(ArrayList<OpLog> opLogsToRemove) {
        super(opLogsToRemove);
    }

    @Override
    public void recover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /**
     * Undo a single operation
     * @param opLog the opLog entry specifying the operation that should be removed 
     */
    private void undoOperation(OpLog opLog){
        ArrayList<OpLog> dependencies = getDependencies(opLog);
    }
    
    /**
     * Finds all the related operations of a given opLog
     * @param opLog
     * @return 
     */
    private ArrayList<OpLog> getDependencies(OpLog opLog){
        return null;
    }
    
    
}
