/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

import java.sql.Timestamp;
import org.bson.Document;

/**
 *
 * @author davidmatos
 */
public class OpLog {
    private long ts;
    private long ordinal;
    private char op;
    private String ns;
    private Document o;

    public OpLog(long ts, long ordinal, char op, String ns, Document o) {
        this.ts = ts;
        this.ordinal = ordinal;
        this.op = op;
        this.ns = ns;
        this.o = o;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(long ordinal) {
        this.ordinal = ordinal;
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
    
    
    
            
}
