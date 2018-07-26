/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.inescid.nosqlundo.recovery;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonTimestamp;
import org.bson.Document;
import org.bson.types.ObjectId;
import pt.inescid.nosqlundo.MongoUndo;

/**
 *
 * @author davidmatos
 */
public class OpLog {

    private boolean isToRemove;
    private BsonTimestamp ts;
    private char op;
    private String ns;
    private Document o;
    private Document o2;

    public OpLog(BsonTimestamp ts, char op, String ns, Document o) {
        this.ts = ts;
        this.op = op;
        this.ns = ns;
        this.o = o;
        this.isToRemove = false;
        this.o2 = null;
    }

    public BsonTimestamp getTs() {
        return ts;
    }

    public void setTs(BsonTimestamp ts) {
        this.ts = ts;
    }

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public Document getO() {
        return o;
    }

    public void setO(Document o) {
        this.o = o;
    }

    public boolean isIsToRemove() {
        return isToRemove;
    }

    public void setIsToRemove(boolean isToRemove) {
        this.isToRemove = isToRemove;
    }

    public Document getO2() {
        return o2;
    }

    public void setO2(Document o2) {
        this.o2 = o2;
    }

    public Object[] getRow() {
        Object[] result = new Object[6];
        result[0] = isToRemove;
        result[1] = ts;
        result[2] = op;
        result[3] = ns;
        result[4] = o;
        result[5] = this.o2 == null ? "" : this.o2;
        return result;
    }

    public void execute(String databaseName) {
        if (!ns.contains(".")) {
            return;
        }

        if (op == 'c') {
            if (this.o.containsKey("create")) {

//                System.out.println("Created collectio: " + this.o.get("create").toString());
            } else if (this.o.containsKey("drop")) {
//                System.out.println("Drop database: " + this.o.get("drop").toString());
               // MongoUndo.mongoClient.getDatabase(this.o.get("drop").toString()).drop();
            }

            return;
        }
        MongoDatabase database = MongoUndo.mongoClient.getDatabase(databaseName);

        MongoCollection collection = database.getCollection(ns.split("\\.")[1]);
        switch (this.op) {
            case 'i':
                
                if(collection.find(new Document("_id", this.getO().get("_id"))).first() == null){
                collection.insertOne(this.o);
//                System.out.println("Inserted document");
                }
                
                
                break;
            case 'u':
                this.o.remove("_id");
                Document oldDocument = (Document) collection.find(this.o2).first();

                if (this.o.containsKey("$set")) {
                    Document setParams = (Document) this.o.get("$set");
                    for (String key : setParams.keySet()) {

                        oldDocument.put(key, setParams.get(key));

                    }
                } else {
                    oldDocument = this.o;
                }
//                System.out.println("Vou atualizar: " + this.o2.toString() + " com " + oldDocument.toString());
                collection.updateOne(this.o2, new Document("$set", oldDocument));
                
//                System.out.println("Updated document");
                break;
            case 'd':
                collection.deleteOne(this.o);
//                System.out.println("Deleted document");
            default:

                break;
        }
    }

    @Override
    public String toString() {
        String result = "Operation ";
        if(op == 'i'){
            result += "insert ";
        }
        if(op == 'u'){
            result += "update ";
        }
        if(op == 'd'){
            result += "delete ";
        }
        
        result += "in coollection " + ns.split("\\.")[1];
            
        return result;
    }
    
    

}
