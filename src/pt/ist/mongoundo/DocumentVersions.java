/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author davidmatos
 */
public class DocumentVersions {
    private ArrayList<HashMap<String, Object>> rows;
    private ArrayList<String> headers;

    public DocumentVersions() {
        this.rows = new ArrayList<>();
        this.headers = new ArrayList<> ();
    }
    public DocumentVersions(ArrayList<HashMap<String, Object>> rows, ArrayList<String> headers, boolean deleted) {
        this.rows = rows;
        this.headers = headers;
    }

    public ArrayList<HashMap<String, Object>> getRows() {
        return rows;
    }

    public void setRows(ArrayList<HashMap<String, Object>> rows) {
        this.rows = rows;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    
    public void addHeader(String newHeader){
        if(!this.headers.contains(newHeader)){
            this.headers.add(newHeader);
        }
    }
    
    public void addRow(HashMap<String, Object> newRow){
        if(newRow.containsKey("_deleted")){
            this.rows.add(new HashMap<String, Object>());
        }else{
            this.rows.add(newRow);
        }
        
        System.out.println("Adicionei row, agora tenho : " + this.rows.size());
    }
    
    
    
}
