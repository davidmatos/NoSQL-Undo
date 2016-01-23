/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo;

import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import java.util.HashMap;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author davidmatos
 */
public class MongoUndoUtils {

    public static DocumentVersions getDocumentVersions(String database, String collection, String _id) {

        DocumentVersions documentVersions = new DocumentVersions();

        String ns = database + "." + collection;
        ObjectId id = new ObjectId(_id);
        HashMap<String, Object> whereMap = new HashMap<>();

        whereMap.put("ns", ns);

        Document o = new Document("o._id", id);
        Document o2 = new Document("o2._id", id);

        ArrayList<Document> oArray = new ArrayList<>();
        oArray.add(o);
        oArray.add(o2);
        whereMap.put("$or", oArray);

        Document where = new Document(whereMap);
        FindIterable<Document> logEntries = MongoUndo.mongoClient.
                getDatabase(MongoUndoConstants.LOCAL_DB).
                getCollection(MongoUndoConstants.OP_LOG_TABLE).find(where).sort(new Document("ts", 1));

        documentVersions.addHeader("Version");

        HashMap<String, Object> row = new HashMap<>();
        for (Document logEntry : logEntries) {
            //System.out.println("LOG ENTREYYYYYYYYYYYYY_______-------_______------_");
            Document document = (Document) logEntry.get("o");

            if (logEntry.get("op").equals("i")) {
                //insert operation
                documentVersions.setFullReconstruction(true);
            } else if (logEntry.get("op").equals("u")) {
                //update operation
                document = (Document) document.get("$set");

            } else if (logEntry.get("op").equals("d")) {
                //delete operation
                documentVersions.setDeleted(true);
            }
            if (document != null) {
                for (String key : document.keySet()) {
                    row.put(key, document.get(key));
                    documentVersions.addHeader(key);

                }
            }
            if (logEntry.containsKey("o2")) {
                document = (Document) logEntry.get("o2");
                for (String key : document.keySet()) {
                    row.put(key, document.get(key));
                    documentVersions.addHeader(key);
                }
            }
            documentVersions.addRow((HashMap<String, Object>) row.clone());

        }

        if (!documentVersions.isFullReconstruction() && !documentVersions.isDeleted()) {
            FindIterable<Document> documentIT = MongoUndo.mongoClient.getDatabase(database).getCollection(collection).find(new Document("_id", new ObjectId(_id)));
            //tenho de completar o doc
            Document document = documentIT.first();
            for (int i = documentVersions.getRows().size() - 1; i >= 0; i--) {
                HashMap<String, Object> r = documentVersions.getRows().get(i);
                r.keySet().stream().forEach((key) -> {
                    document.remove(key);
                });
                document.keySet().stream().forEach((key) -> {
                    r.put(key, document.get(key));
                });

            }
        }


        return documentVersions;
    }
}
