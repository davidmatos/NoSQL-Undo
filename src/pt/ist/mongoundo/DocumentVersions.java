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
    private boolean fullReconstruction;
    private boolean deleted;

    public DocumentVersions() {
        this.rows = new ArrayList<>();
        this.headers = new ArrayList<> ();
        this.fullReconstruction = false;
        this.deleted = false;
    }
    public DocumentVersions(ArrayList<HashMap<String, Object>> rows, ArrayList<String> headers, boolean fullReconstruction, boolean deleted) {
        this.rows = rows;
        this.headers = headers;
        this.fullReconstruction = fullReconstruction;
        this.deleted = deleted;
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

    public boolean isFullReconstruction() {
        return fullReconstruction;
    }

    public void setFullReconstruction(boolean fullReconstruction) {
        this.fullReconstruction = fullReconstruction;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public void addHeader(String newHeader){
        if(!this.headers.contains(newHeader)){
            this.headers.add(newHeader);
        }
    }
    
    public void addRow(HashMap<String, Object> newRow){
        this.rows.add(newRow);
        System.out.println("Adicionei row, agora tenho : " + this.rows.size());
    }
    
    
    
}
