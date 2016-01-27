/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.where;
import java.util.ArrayList;
import java.util.HashMap;
import org.bson.Document;
import org.bson.types.ObjectId;
import pt.ist.mongoundo.MongoUndo;
import pt.ist.mongoundo.MongoUndoConstants;

/**
 *
 * @author davidmatos
 */
public class RecoveryUtils {
    
    
    public static FindIterable<Document> getDocumentLogEntries(String database, String collection, String _id){
        HashMap<String, Object> whereMap = new HashMap<String, Object>();
String ns = database + "." + collection;
        ObjectId id = new ObjectId(_id);
        whereMap.put("ns", ns);

        Document o = new Document("o._id", id);
        Document o2 = new Document("o2._id", id);

        ArrayList<Document> oArray = new ArrayList<Document>();
        oArray.add(o);
        oArray.add(o2);
        whereMap.put("$or", oArray);
        Document where = new Document(whereMap);
        FindIterable<Document> itLogEntries = MongoUndo.mongoClient.
                getDatabase(MongoUndoConstants.LOCAL_DB).
                getCollection(MongoUndoConstants.OP_LOG_TABLE).find(where)
                .sort(new Document("ts", -1));
        return itLogEntries;
    }

    
}
