/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ist.mongoundo.gui;

import com.mongodb.MongoClient;
import java.awt.Frame;
import java.util.HashMap;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import pt.ist.mongoundo.MongoConnection;
import pt.ist.mongoundo.MongoUndo;

/**
 *
 * @author davidmatos
 */
public class JDialogConnections extends javax.swing.JDialog {

    private HashMap<String, MongoConnection> connections;

    /**
     * Creates new form JDialogConnections
     */
    public JDialogConnections(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.connections = MongoUndo.getConnectionsList();
        initComponents();
        populateConnectionsList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstConnections = new javax.swing.JList<>();
        btnNew = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lstConnections.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstConnections);

        btnNew.setText("New...");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConnect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        newConnection();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        connect();
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editConnection();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        removeConnection();
    }//GEN-LAST:event_btnRemoveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemove;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lstConnections;
    // End of variables declaration//GEN-END:variables

    private void populateConnectionsList() {
        HashMap<String, MongoConnection> connections = MongoUndo.getConnectionsList();
        DefaultListModel listModel = new DefaultListModel<String>();
        lstConnections.removeAll();
        Set<String> keys = connections.keySet();
        for (String key : keys) {
            MongoConnection connection = connections.get(key);
            listModel.addElement(connection.getConnectionName());
        }
        // lstConnections.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        lstConnections.setLayoutOrientation(JList.VERTICAL);
        lstConnections.setVisibleRowCount(-1);
        lstConnections.setModel(listModel);
    }

    private MongoConnection getSelectedMongoConnection(HashMap<String, MongoConnection> connections) {
        for (String key : connections.keySet()) {
            if (connections.get(key).getConnectionName()
                    .equals(lstConnections.getSelectedValue().toString())) {

                return connections.get(key);

            }

        }
        return null;
    }

    private void newConnection() {
        JDialogConnection jDialogConnection = new JDialogConnection((Frame) this.getParent(), true, lstConnections);
        jDialogConnection.setVisible(true);
    }

    private void editConnection() {
        if (lstConnections.getSelectedValue() == null) {
            return;
        }

        MongoConnection mongoConnection = getSelectedMongoConnection(connections);
        JDialogConnection jDialogConnection = new JDialogConnection((Frame) getParent(), true, lstConnections);
        jDialogConnection.setMongoConnection(mongoConnection);
        jDialogConnection.setVisible(true);
    }

    private void connect() {
        if (lstConnections.getSelectedValue() == null) {
            return;
        }

        // Connects to the database
        this.setVisible(false);
        MongoClient mongoClient;
        try {

//            lblState.setText("Connecting to " + mongoConnection.getServerAddress() + ":" + mongoConnection.getServerPort());
            System.out.println("Going to connect to : " + getSelectedMongoConnection(connections).getServerAddress() + ":" + getSelectedMongoConnection(connections).getServerPort());
            mongoClient = new MongoClient(getSelectedMongoConnection(connections).getServerAddress(), getSelectedMongoConnection(connections).getServerPort());
        } catch (Exception e) {
//            lblState.setText("<html>" + lblState.getText() + "<br>Failed to connect to database</html>");
            e.printStackTrace();
            return;
        }
        System.out.println("connected: " + mongoClient.getConnectPoint());
//        lblState.setText("<html>" + lblState.getText() + "<br>Successfuly connected to " + mongoConnection.getServerAddress() + ":" + mongoConnection.getServerPort() + "</html>");

        MongoUndo.setMongoClient(mongoClient);

        this.setVisible(false);
    }

    private void removeConnection() {
        Set<String> keys = connections.keySet();
        System.out.println(lstConnections.getSelectedValue().toString());
        for (String key : keys) {
            if (connections.get(key).getConnectionName()
                    .equals(lstConnections.getSelectedValue().toString())) {
                MongoUndo.removeConnection(connections.get(key));
                populateConnectionsList();
                return;
            }
        }
    }
}
