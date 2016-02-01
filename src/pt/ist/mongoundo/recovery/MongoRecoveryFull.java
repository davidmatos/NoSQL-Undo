/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import java.util.ArrayList;
import java.util.Calendar;
import org.bson.BsonTimestamp;
import pt.ist.mongoundo.MongoUndo;

/**
 *
 * @author davidmatos
 */
public class MongoRecoveryFull extends MongoRecovery {

    public MongoRecoveryFull(ArrayList<OpLog> opLogsToRemove, String database) {
        super(opLogsToRemove, database);

    }

    @Override
    public void recover() {
        if (MongoUndo.jFrameMain != null) {
            MongoUndo.jFrameMain.disableRecoveryButtons();
        }

        Calendar cal = Calendar.getInstance();
        String databaseName = getDatabase() + "_recovered_"
                + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH)
                + cal.get(Calendar.DATE) + "__" + cal.get(Calendar.HOUR_OF_DAY)
                + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
        ArrayList<BsonTimestamp> opLogsToRemove = new ArrayList<>();
        for (OpLog opLog : this.getOpLogsToRemove()) {
            opLogsToRemove.add(opLog.getTs());
        }
        ArrayList<OpLog> opLogs = OpLogUtils.getDatabaseOplogs(getDatabase());
        if (MongoUndo.jFrameMain != null) {
            MongoUndo.jFrameMain.setNrOperations(opLogs.size() - opLogsToRemove.size());
        }

        
        
        opLogs.stream().forEach((oplog) -> {
            if (!opLogsToRemove.contains(oplog.getTs())) {
                if (MongoUndo.jFrameMain != null) {
                    MongoUndo.jFrameMain.setCurrentOperation(oplog.toString());
                }
                
                oplog.execute(databaseName);    
                
                
            }
        });

        System.out.println("Finished recovering");
        if (MongoUndo.jFrameMain != null) {
            MongoUndo.jFrameMain.populateTree();

            MongoUndo.jFrameMain.enableRecoveryButtons();
        }

    }

}
