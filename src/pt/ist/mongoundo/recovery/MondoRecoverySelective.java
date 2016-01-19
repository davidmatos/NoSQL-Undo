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
public class MondoRecoverySelective extends MongoRecovery {
    
    public MondoRecoverySelective(ArrayList<OpLog> opLogsToRemove) {
        super(opLogsToRemove);
    }

    @Override
    public void recover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
