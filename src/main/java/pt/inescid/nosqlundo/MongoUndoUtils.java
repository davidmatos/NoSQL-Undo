/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.inescid.nosqlundo;

import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import java.util.HashMap;
import org.bson.Document;
import org.bson.types.ObjectId;
import pt.inescid.nosqlundo.recovery.RecoveryUtils;

/**
 *
 * @author davidmatos
 */
public class MongoUndoUtils {

    public static DocumentVersions getDocumentVersions(String database, String collection, ObjectId _id) {

        DocumentVersions documentVersions = new DocumentVersions();

       
        FindIterable<Document> logEntries = RecoveryUtils.getDocumentLogEntries(database, collection, _id, 1);

        //Let's start the construction of every version
        documentVersions.addHeader("Version");
        Document currentVersion = new Document();
        
        for (Document logEntry : logEntries) {
            HashMap<String, Object> row = new HashMap<>();

            if (logEntry.get("op").equals("i")) {
                //insert operation
                currentVersion = (Document) logEntry.get("o");
            } else if (logEntry.get("op").equals("u")) {
                //update operation
                //document = (Document) currentVersion.get("$set");
                if (((Document)logEntry.get("o")).containsKey("$set")) {
                    Document updateSet = (Document) ((Document)logEntry.get("o")).get("$set");
                    for (String key : updateSet.keySet()) {
                        currentVersion.put(key, updateSet.get(key));
                        documentVersions.addHeader(key);
                    }
                } else {
                    currentVersion = (Document) logEntry.get("o");
                }
                
                Document updateSet = (Document) logEntry.get("o2");
                for (String key : updateSet.keySet()) {
                    currentVersion.put(key, updateSet.get(key));
                    documentVersions.addHeader(key);
                }
            

            } else if (logEntry.get("op").equals("d")) {
                //delete operation
                //currentVersion = new Document();
               // currentVersion.put("_deleted", true);
//                for (String key : currentVersion.keySet()) {
//                    currentVersion.remove(key);
//                }
                   documentVersions.addRow(new HashMap<String, Object>());
                   continue;
            }

            
            for (String key : currentVersion.keySet()) {
                row.put(key, currentVersion.get(key));
                documentVersions.addHeader(key);

            }
            
            documentVersions.addRow((HashMap<String, Object>) row.clone());

        }

//        if (!documentVersions.isFullReconstruction() && !documentVersions.isDeleted()) {
//            FindIterable<Document> documentIT = MongoUndo.mongoClient.getDatabase(database).getCollection(collection).find(new Document("_id", new ObjectId(_id)));
//            //tenho de completar o doc
//            Document currentVersion = documentIT.first();
//            for (int i = documentVersions.getRows().size() - 1; i >= 0; i--) {
//                HashMap<String, Object> r = documentVersions.getRows().get(i);
//                r.keySet().stream().forEach((key) -> {
//                    currentVersion.remove(key);
//                });
//                currentVersion.keySet().stream().forEach((key) -> {
//                    r.put(key, currentVersion.get(key));
//                });
//
//            }
//        }
        return documentVersions;
    }
}
