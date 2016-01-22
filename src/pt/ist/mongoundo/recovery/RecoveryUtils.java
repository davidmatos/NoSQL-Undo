/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.recovery;

/**
 *
 * @author davidmatos
 */
public class RecoveryUtils {
    
    public static void cenas(){
        String query = "db.oplog.$main.find({ \"o._id\":  ObjectId(\"566ff5cd31224f9fbac0bea4\")})";
    }
    
}
