/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.awt.Dialog;
import java.util.ArrayList;
import javax.swing.JDialog;
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
        ArrayList<BsonTimestamp> opLogsToRemove = new ArrayList<>();
        for(OpLog opLog: this.getOpLogsToRemove()){
            opLogsToRemove.add(opLog.getTs());
        }
        //String recoveredDatabaseName = "recovered_" + new Date().toString().replaceAll(":", "").replaceAll(" ", "");
        MongoDatabase database = MongoUndo.mongoClient.getDatabase(getDatabase());
        MongoIterable<String> collectionNames = database.listCollectionNames();
        
        for(String collectionName : collectionNames){
            if(collectionName.contains("system.indexes")){
                continue;
            }

            database.getCollection(collectionName).drop();
        }   
        ArrayList<OpLog> opLogs = OpLogUtils.getDatabaseOplogs(getDatabase());
        opLogs.stream().forEach((oplog) -> {
            if(!opLogsToRemove.contains(oplog.getTs())){
                MongoUndo.jFrameMain.addRecoveryProgressMessage("Executed " + oplog.getOp() + " operation");
                oplog.execute();
            } 
        });
        MongoUndo.jFrameMain.addRecoveryProgressMessage("Finished recovering");
        
         
    }
    
}
