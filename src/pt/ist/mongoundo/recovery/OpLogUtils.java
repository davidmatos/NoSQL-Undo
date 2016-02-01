/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import org.bson.BsonTimestamp;
import org.bson.Document;
import pt.ist.mongoundo.MongoUndo;

/**
 *
 * @author davidmatos
 */
public class OpLogUtils {

    public static ArrayList<OpLog> getCollectionOplogs(String database, String collection) {
        String namespace = database + "." + collection;
        FindIterable<Document> it = MongoUndo.mongoClient.getDatabase("local").getCollection("oplog.$main")
                .find(new Document("ns", namespace));
        ArrayList<OpLog> opLogs = new ArrayList<>();
        for (Document logEntry : it) {
            OpLog opLog = new OpLog(
                    (BsonTimestamp) logEntry.get("ts"),
                    logEntry.getString("op").charAt(0),
                    namespace,
                    (Document) logEntry.get("o"));
            if (logEntry.containsKey("o2")) {
                opLog.setO2((Document) logEntry.get("o2"));
            }
            opLogs.add(opLog);
        }

        return opLogs;
    }

    public static ArrayList<OpLog> getDatabaseOplogs(String database) {
        Document regexQuery = new Document();

        regexQuery.put("ns",
                new Document("$regex", database + "\\.*"));
        FindIterable<Document> it = MongoUndo.mongoClient.getDatabase("local").getCollection("oplog.$main")
                .find(regexQuery).sort(new Document("ts", 1));
        ArrayList<OpLog> opLogs = new ArrayList<>();
        for (Document logEntry : it) {
            if (logEntry.get("ns").toString().startsWith(database + ".")) {

                OpLog opLog = new OpLog(
                        (BsonTimestamp) logEntry.get("ts"),
                        logEntry.getString("op").charAt(0),
                        logEntry.getString("ns"),
                        (Document) logEntry.get("o"));
                if (logEntry.containsKey("o2")) {
                    opLog.setO2((Document) logEntry.get("o2"));
                }
                opLogs.add(opLog);
            }

        }

        return opLogs;
    }
    
    
    
    
    
    public static ArrayList<OpLog> getDatabaseOplogs(String database, int n) {
        Document regexQuery = new Document();

        regexQuery.put("ns",
                new Document("$regex", database + "\\.*"));
        regexQuery.put("op", "i");
        FindIterable<Document> it = MongoUndo.mongoClient.getDatabase("local").getCollection("oplog.$main")
                .find(regexQuery).sort(new Document("ts", 1)).limit(n);
        ArrayList<OpLog> opLogs = new ArrayList<>();
        for (Document logEntry : it) {
            if (logEntry.get("ns").toString().startsWith(database + ".")) {

                OpLog opLog = new OpLog(
                        (BsonTimestamp) logEntry.get("ts"),
                        logEntry.getString("op").charAt(0),
                        logEntry.getString("ns"),
                        (Document) logEntry.get("o"));
                if (logEntry.containsKey("o2")) {
                    opLog.setO2((Document) logEntry.get("o2"));
                }
                opLogs.add(opLog);
                if(opLogs.size() == n){
                    return opLogs;
                }
            }

        }

        return opLogs;
    }

}
