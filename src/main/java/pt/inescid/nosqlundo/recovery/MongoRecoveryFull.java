/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.inescid.nosqlundo.recovery;

import java.util.ArrayList;
import java.util.Calendar;
import org.bson.BsonTimestamp;
import pt.inescid.nosqlundo.MongoUndo;

/**
 *
 * @author davidmatos
 */
public class MongoRecoveryFull extends MongoRecovery {

    private ArrayList<OpLog> opLogsToKeep;

    public MongoRecoveryFull(ArrayList<OpLog> opLogsToKeep, String database) {
        super(null, database);
        this.opLogsToKeep = opLogsToKeep;

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

        //ArrayList<OpLog> opLogs = OpLogUtils.getDatabaseOplogs(getDatabase());
        if (MongoUndo.jFrameMain != null) {
            MongoUndo.jFrameMain.setNrOperations(this.opLogsToKeep.size());
        }

        this.opLogsToKeep.stream().forEach((oplog) -> {

            if (MongoUndo.jFrameMain != null) {
                MongoUndo.jFrameMain.setCurrentOperation(oplog.toString());
            }

            oplog.execute(databaseName);

        });

        System.out.println("Finished recovering");
        if (MongoUndo.jFrameMain != null) {
            MongoUndo.jFrameMain.populateTree();

            MongoUndo.jFrameMain.enableRecoveryButtons();
        }

    }

}
