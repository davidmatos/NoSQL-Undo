package pt.ist.mongoundo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.swing.JFrame;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.Date;
import pt.ist.mongoundo.gui.JFrameMain;
import pt.ist.mongoundo.recovery.MongoRecoveryFull;
import pt.ist.mongoundo.recovery.MongoRecoveryUndo;
import pt.ist.mongoundo.recovery.OpLog;
import pt.ist.mongoundo.recovery.OpLogUtils;

public class MongoUndo {

    private static HashMap<String, MongoConnection> connections = null;

    public static MongoClient mongoClient;

    public static JFrameMain jFrameMain;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        mongoClient = new MongoClient("localhost", 27017);
        if (args.length > 2) {
            System.out.println("Starting sim");
            int n = 0;
            String mode = "";
            ArrayList<OpLog> opLogsToRemove = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-n")) {
                    i++;
                    n = Integer.parseInt(args[i]);
                    
                }
                if(args[i].equals("--mode")){
                    i++;
                    long time = 0;
                    mode = args[i];
                    if(args[i].equals("undo")){
                        opLogsToRemove = OpLogUtils.getDatabaseOplogs("ycsb", n);
                        MongoRecoveryUndo mongoRecoveryUndo = new MongoRecoveryUndo(opLogsToRemove, "ycsb");
                        Date dtStart = new Date();
                        mongoRecoveryUndo.recover();
                        Date dtFinish = new Date();
                        time = dtFinish.getTime() - dtStart.getTime();
                                
                    }else{
                        opLogsToRemove = OpLogUtils.getDatabaseOplogs("ycsb", 100000-n);
                        MongoRecoveryFull mongoRecoveryFull = new MongoRecoveryFull(opLogsToRemove, "ycsb");
                        Date dtStart = new Date();
                        mongoRecoveryFull.recover();
                        Date dtFinish = new Date();
                        time = dtFinish.getTime() - dtStart.getTime();
                    }
                    
                    System.out.println("Finished "+mode+" recovery with " + n + " opLogsToRemove in " + time + "ms");
                }
            }

        } else {

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    jFrameMain = new JFrameMain();
                    jFrameMain.setVisible(true);

                    jFrameMain = new JFrameMain();
                    jFrameMain.setVisible(true);
                    jFrameMain.setExtendedState(jFrameMain.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                }
            });
        }
    }

    public static HashMap<String, MongoConnection> getConnectionsList() {
        File file = new File(MongoUndoConstants.CONNECTIONS_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connections = new HashMap<String, MongoConnection>();
            return connections;
        }
        if (connections == null) {

            connections = new HashMap<String, MongoConnection>();
            //File f = new File(MongoUndoConstants.CONNECTIONS_FILE);
            try {
                InputStream inputStream = new BufferedInputStream(new FileInputStream(MongoUndoConstants.CONNECTIONS_FILE));
                ObjectInput objectInput = new ObjectInputStream(inputStream);

                connections = (HashMap<String, MongoConnection>) objectInput.readObject();
                objectInput.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        System.out.println("Loaded " + connections.size() + " connections");
        return connections;
    }

    public static void saveConnectionsList() {
        OutputStream file;
        try {
            file = new FileOutputStream(MongoUndoConstants.CONNECTIONS_FILE);

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(connections);
            output.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void addConnection(MongoConnection connection) {
        connections.put(connection.getTimestamp(), connection);
        saveConnectionsList();
    }

    public static void removeConnection(MongoConnection connection) {
        connections.remove(connection.getTimestamp());
        saveConnectionsList();
    }

    public static void updateConnection(MongoConnection connection) {
        connections.put(connection.getTimestamp(), connection);
        saveConnectionsList();
    }

    public static void setMongoClient(MongoClient client) {
        mongoClient = client;
        jFrameMain.populateTree();
    }

}
