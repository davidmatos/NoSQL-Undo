/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;
import pt.ist.mongoundo.MongoUndo;

/**
 *
 * @author davidmatos
 */
public class MongoRecoveryUndo extends MongoRecovery {

    public MongoRecoveryUndo(ArrayList<OpLog> opLogsToRemove, String database) {
        super(opLogsToRemove, database);
    }

    @Override
    public void recover() {
        if(MongoUndo.jFrameMain != null){
        MongoUndo.jFrameMain.disableRecoveryButtons();    
        }
        

        for (OpLog oplogToRemove : getOpLogsToRemove()) {
            String databaseName = oplogToRemove.getNs().split("\\.")[0];
            String collectionName = oplogToRemove.getNs().split("\\.")[1];
            
            if (oplogToRemove.getOp() == 'i') {
                MongoUndo.mongoClient.getDatabase(databaseName).getCollection(collectionName).deleteOne(oplogToRemove.getO());
                continue;
            }
//            ArrayList<OpLog> documentOpLogs = RecoveryUtils.getDocumentOpLogs(oplogToRemove.getNs().split("\\.")[0],
//                    oplogToRemove.getNs().split("\\.")[1], oplogToRemove.getO().getObjectId("_id").toString(), 1);
            
            
            ArrayList<OpLog> documentOpLogs = RecoveryUtils.getDocumentOpLogs(oplogToRemove.getNs().split("\\.")[0],
                    oplogToRemove.getNs().split("\\.")[1], oplogToRemove.getO().get("_id"), 1);
            
            Document documentRecovered = new Document();
            //start reconstructing the document
            if(MongoUndo.jFrameMain != null){
                MongoUndo.jFrameMain.setNrOperations(documentOpLogs.size());
            }
            
            
            for (OpLog oplog : documentOpLogs) {
                if(MongoUndo.jFrameMain != null){
                MongoUndo.jFrameMain.setCurrentOperation("Reverting " + oplog.toString());
                }
                if (!oplog.getTs().equals(oplogToRemove.getTs())) {
                    //if it's not the one to remove
                    Document updateSet = new Document();
                    if (oplog.getOp() == 'i') {
                        updateSet = oplog.getO();
                    } else if (oplog.getOp() == 'u') {
                        if (oplog.getO().containsKey("$set")) {
                            updateSet = (Document) oplog.getO().get("$set");
                        } else {
                            updateSet = oplog.getO();
                        }
                    }

                    for (String key : updateSet.keySet()) {
                        documentRecovered.put(key, updateSet.get(key));
                    }

                }
            }

            ObjectId _id = (ObjectId) documentRecovered.get("_id");
            MongoUndo.mongoClient.getDatabase(databaseName).getCollection(collectionName).deleteOne(new Document("_id", _id));
            MongoUndo.mongoClient.getDatabase(databaseName).getCollection(collectionName).insertOne(documentRecovered);
            System.out.println("Undo operation");
        }
        if(MongoUndo.jFrameMain != null){
        MongoUndo.jFrameMain.setCurrentOperation("Recovery completed");

        MongoUndo.jFrameMain.enableRecoveryButtons();
        }
        
    }

 

}
