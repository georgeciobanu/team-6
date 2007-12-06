package gui;
/*
 * TabbedPane2.java
 *
 * Created on November 20, 2007, 7:26 PM
 */

/**
 *
 * @author  kselik
 */

import fundamentals.*;
import javax.swing.*;
import clientnetworking.*;
import java.util.*;
import javax.swing.table.*;

public class TabbedPane2 extends javax.swing.JPanel {
    ClientNetworkInterface m_cni;
    JFrame owner;
    JPanel menu;
    
    private JScrollPane m_spPendingOrder;
    private JTable m_table;
    private MyTableModel m_tableModel;
    
    /** Creates new form TabbedPane2 */
    public TabbedPane2(JPanel menu, JFrame owner, ClientNetworkInterface cni) {
        m_cni = cni;
        
        this.owner=owner;
        this.menu=menu;
        initComponents();
        
        initPendingOrderTable();
    }
    
    private void initPendingOrderTable() {
        //the code for the table was taken from http://forum.java.sun.com/thread.jspa?threadID=5242581&tstart=1
        m_spPendingOrder = new JScrollPane();
        
        panPendingOrders.add(m_spPendingOrder);
        
        {
            m_table = new JTable();
            //m_spPendingOrder.setViewportView(m_table);
            jScrollPane1.setViewportView(m_table);
            m_tableModel = new MyTableModel();
            
            // Create a couple of columns
            Vector colNames = new Vector();
            colNames.add("ID");
            colNames.add("Type");
            colNames.add("CurrencyPair");
            colNames.add("BuySell");
            colNames.add("Amount");
            colNames.add("Expiry");
            m_tableModel.setColumnNames(colNames);
            
            m_table.setModel(m_tableModel);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panPendingOrders = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPendingOrders = new javax.swing.JTable();
        btnEditOrder = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        btnUpdatePendingOrders = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnBackToMainMenu = new javax.swing.JButton();

        tblPendingOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Type", "Currency Pair", "Buy/Sell", "Amount", "Expiry"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPendingOrders);

        btnEditOrder.setText("Edit Order");
        btnEditOrder.setEnabled(false);
        btnEditOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOrderActionPerformed(evt);
            }
        });

        btnCancelOrder.setText("Cancel Order");
        btnCancelOrder.setEnabled(false);
        btnCancelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelOrderActionPerformed(evt);
            }
        });

        btnUpdatePendingOrders.setText("Update");
        btnUpdatePendingOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdatePendingOrdersMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panPendingOrdersLayout = new javax.swing.GroupLayout(panPendingOrders);
        panPendingOrders.setLayout(panPendingOrdersLayout);
        panPendingOrdersLayout.setHorizontalGroup(
            panPendingOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPendingOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panPendingOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                    .addGroup(panPendingOrdersLayout.createSequentialGroup()
                        .addComponent(btnCancelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(483, 483, 483))
                    .addComponent(btnUpdatePendingOrders))
                .addContainerGap())
        );
        panPendingOrdersLayout.setVerticalGroup(
            panPendingOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPendingOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUpdatePendingOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panPendingOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelOrder)
                    .addComponent(btnEditOrder))
                .addContainerGap())
        );
        jTabbedPane1.addTab("Pending Orders", panPendingOrders);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Currency:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(37, 37, 37))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );
        jTabbedPane1.addTab("Order History", jPanel2);

        btnBackToMainMenu.setText("Back to Main Menu");
        btnBackToMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToMainMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(btnBackToMainMenu))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBackToMainMenu)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void getPendingOrdersID() {
        String message;
        String args[];
        int orderid = -1;
        
        for(; m_tableModel.getRowCount() > 0;) {
            m_tableModel.removeRowAt(0);
        }
        
        m_cni.SendMessage("getpendingorders");
        while((message = m_cni.ReceiveMessage()).equals("") && m_cni.isConnected());
        args = message.split(" ");
        
        if(args.length >= 3 && args[0].equals("ok") && args[1].equals("getpendingorders")) {
            Vector v = new Vector();
            
            for(int i = 2; i < args.length; i++) {
                v.add(Integer.valueOf(args[i]));
            }
            
            if(v != null) {
                for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
                    // Append a row
                    Vector w = new Vector();

                    orderid = (Integer)e.nextElement();
                    
                    m_cni.SendMessage("getorder " + String.valueOf(orderid));
                    while((message = m_cni.ReceiveMessage()).equals("") && m_cni.isConnected());
                    
                    if(message.startsWith("ok getorder ")) {
                        message = message.substring("ok getorder ".length());
                        
                        Order o = new Order(message);
                        
                        w.add(orderid);
                        w.add(o.getTypeStringExplicit());
                        w.add(o.getCurrencyPair().toString());
                        w.add(o.getOperationStringExplicit());
                        w.add(o.getAmount());
                        w.add(o.getDuration());
                        m_tableModel.addRow(w);
                    }
                }
            }
        }
    }
    
    private void btnUpdatePendingOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdatePendingOrdersMouseClicked
        getPendingOrdersID();
    }//GEN-LAST:event_btnUpdatePendingOrdersMouseClicked

    private void btnBackToMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToMainMenuActionPerformed

      this.setVisible(false);
      owner.setContentPane(menu);
      menu.setVisible(true);

    }//GEN-LAST:event_btnBackToMainMenuActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnCancelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelOrderActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_btnCancelOrderActionPerformed

    private void btnEditOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOrderActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_btnEditOrderActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackToMainMenu;
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnEditOrder;
    private javax.swing.JButton btnUpdatePendingOrders;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel panPendingOrders;
    private javax.swing.JTable tblPendingOrders;
    // End of variables declaration//GEN-END:variables
    
}
