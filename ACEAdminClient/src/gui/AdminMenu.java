package gui;
/*
 * AdminMenu.java
 *
 * Created on November 30, 2007, 5:16 PM
 */

/**
 *
 * @author  icioba1
 */

import fundamentals.*;
import javax.swing.*;
import clientnetworking.*;
import java.util.*;
import javax.swing.table.*;

public class AdminMenu extends javax.swing.JPanel {
    ClientNetworkInterface m_cni;
    String[] m_currenciesList;
    String[] m_currencyPairsList;
    
    private JScrollPane m_spUsernames;
    private JTable m_table;
    private MyTableModel m_tableModel;
    
    JFrame owner;
    JPanel Login;
    
    // Get the available traiding currencies from the ACE system
    private void getCurrencies() {
        String message;
        String args[];
        m_cni.SendMessage("getcurrencies");
        while((message = m_cni.ReceiveMessage()).equals("") && m_cni.isConnected());
        args = message.split(" ");
        
        if(args.length >= 3 && args[0].equals("ok") && args[1].equals("getcurrencies")) {
            m_currenciesList = new String[args.length - 2];
            for(int i = 0; i < args.length - 2; i++) {
                m_currenciesList[i] = args[i + 2];
            }
        } else {
            // Failed to login
            m_cni.SendMessage("logout");
            m_cni.disconnect();
            
            // Go back to LoginPanel
            this.setVisible(false);
            Login.setVisible(true);
            owner.setContentPane(Login);
        }
    }
    
    private String[] getCurrencyPairs() {
        String temp = "";
        for(int i = 0; i < m_currenciesList.length; i++) {
            for(int j = 0; j < m_currenciesList.length; j++) {
                if(i != j) {
                    temp += m_currenciesList[i] + "/" + m_currenciesList[j] + " ";
                }
            }
        }
        return temp.trim().split(" ");
    }
    
    private void getUsernames() {
        String message;
        String args[];
        int orderid = -1;
        
        for(; m_tableModel.getRowCount() > 0;) {
            m_tableModel.removeRowAt(0);
        }
        
        String type = "";
        
        if(choUsertype.getSelectedItem().equals("End-User")) {
            type = "0";
        } else if(choUsertype.getSelectedItem().equals("Administrator")) {
            type = "1";
        } else {
            return;
        }
        
        m_cni.SendMessage("getusernames " + type);
        while((message = m_cni.ReceiveMessage()).equals("") && m_cni.isConnected());
        args = message.split(" ");
        
        if(args.length >= 3 && args[0].equals("ok") && args[1].equals("getusernames")) {
            for(int i = 2; i < args.length; i++) {
                Vector v = new Vector();
                v.add(args[i]);
                if(v != null) {
                    m_tableModel.addRow(v);
                }
            }
        }
    }
    
    private void FillChoice(java.awt.Choice cho, String[] items) {
        for(int i = 0; i < items.length; i++) {
            cho.addItem(items[i]);
        }
    }
    
    /** Creates new form AdminMenu */
    public AdminMenu(JFrame owner, JPanel Login, ClientNetworkInterface cni) {
        
        this.owner=owner;
        this.Login=Login;
        m_cni = cni;
        initComponents();
        
        // Download the available currencies
        getCurrencies();
        
        initUsernamesTable();
        
        // Build up the list of currency pairs available
        m_currencyPairsList = getCurrencyPairs();
        
        FillChoice(choCurrencyManagement, m_currenciesList);
        
        choUsertype.addItem("End-User");
        choUsertype.addItem("Administrator");
        
        getUsernames();
        
    }
    
    private void initUsernamesTable() {
        //the code for the table was taken from http://forum.java.sun.com/thread.jspa?threadID=5242581&tstart=1
        m_spUsernames = new JScrollPane();
        
        panAccountManagement.add(m_spUsernames);
        
        {
            m_table = new JTable();
            //m_spUsernames.setViewportView(m_table);
            jScrollPane1.setViewportView(m_table);
            m_tableModel = new MyTableModel();
            
            // Create a couple of columns
            Vector colNames = new Vector();
            colNames.add("Username");
            m_tableModel.setColumnNames(colNames);
            
            m_table.setModel(m_tableModel);
            
            m_table.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    m_tableMouseClicked(evt);
                }
            });
        }
    }
    
    public void m_tableMouseClicked(java.awt.event.MouseEvent evt) {
        m_table.getSelectedRow();
        String username = (String)m_table.getValueAt(m_table.getSelectedRow(), 0);
        this.setVisible(false);
        EditEndUser edit=new EditEndUser(owner, this, m_cni, username);
        owner.setContentPane(edit);
        edit.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panAccountManagement = new javax.swing.JPanel();
        btnCreateAccount = new javax.swing.JButton();
        choUsertype = new java.awt.Choice();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsernamesTemp = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        btnFeeManagementSubmit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        choCurrencyManagement = new java.awt.Choice();
        btnLogout = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();

        jLabel6.setText("jLabel6");
        jLabel8.setText("jLabel8");

        btnCreateAccount.setText("Create New Account");
        btnCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountActionPerformed(evt);
            }
        });

        choUsertype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choUsertypeItemStateChanged(evt);
            }
        });

        jLabel7.setText("User Type");

        tblUsernamesTemp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Username"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsernamesTemp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsernamesTempMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(tblUsernamesTemp);

        org.jdesktop.layout.GroupLayout panAccountManagementLayout = new org.jdesktop.layout.GroupLayout(panAccountManagement);
        panAccountManagement.setLayout(panAccountManagementLayout);
        panAccountManagementLayout.setHorizontalGroup(
            panAccountManagementLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panAccountManagementLayout.createSequentialGroup()
                .addContainerGap()
                .add(panAccountManagementLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                    .add(panAccountManagementLayout.createSequentialGroup()
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(choUsertype, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnCreateAccount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 151, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panAccountManagementLayout.setVerticalGroup(
            panAccountManagementLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panAccountManagementLayout.createSequentialGroup()
                .addContainerGap()
                .add(panAccountManagementLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel7)
                    .add(btnCreateAccount)
                    .add(choUsertype, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );
        jTabbedPane1.addTab("Account Management", panAccountManagement);

        jLabel2.setText("Default Transaction Fee:");

        jLabel3.setText("Default Interest Rate:");

        jLabel4.setText("Default Leverage Ratio:");

        btnFeeManagementSubmit.setText("Submit");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btnFeeManagementSubmit)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel4)
                            .add(jLabel3))
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextField4, 0, 0, Short.MAX_VALUE))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                                .add(16, 16, 16)
                                .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel2)
                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(29, 29, 29)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnFeeManagementSubmit)
                .addContainerGap(217, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Fee Management", jPanel2);

        jButton3.setText("Add Currency");

        jButton4.setText("Modify Currency");

        jButton5.setText("Remove Currency");

        jLabel1.setText("Name:");

        jLabel9.setText("Choose Currency:");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(choCurrencyManagement, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                    .add(jButton4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                    .add(jButton5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel9)
                    .add(choCurrencyManagement, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton5)
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Currency Management", jPanel1);

        jTabbedPane1.getAccessibleContext().setAccessibleName("AccountManagement");

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnChangePassword.setText("Change Password");
        btnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePasswordActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(btnLogout)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnChangePassword)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnLogout)
                    .add(btnChangePassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void choUsertypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choUsertypeItemStateChanged
        getUsernames();
    }//GEN-LAST:event_choUsertypeItemStateChanged
    
    private void tblUsernamesTempMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsernamesTempMouseClicked
        m_table.getSelectedRow();
        String username = (String)m_table.getValueAt(m_table.getSelectedRow(), 0);
        this.setVisible(false);
        EditEndUser edit=new EditEndUser(owner, this, m_cni, username);
        owner.setContentPane(edit);
        edit.setVisible(true);
    }//GEN-LAST:event_tblUsernamesTempMouseClicked

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePasswordActionPerformed
        AdminChangePassword change = new AdminChangePassword(owner, this, m_cni);
        this.setVisible(false);
        owner.setContentPane(change);
    }//GEN-LAST:event_btnChangePasswordActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        try {
            if(m_cni.isConnected()) {
                m_cni.SendMessage("logout");
                m_cni.disconnect();
            }
            this.setVisible(false);
            Login .setVisible(true);
            owner.setContentPane(Login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountActionPerformed
      UserCreation create=new UserCreation(owner, this, m_cni);
      this.setVisible(false);
      owner.setContentPane(create);
      create.setVisible(true);
    }//GEN-LAST:event_btnCreateAccountActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnCreateAccount;
    private javax.swing.JButton btnFeeManagementSubmit;
    private javax.swing.JButton btnLogout;
    private java.awt.Choice choCurrencyManagement;
    private java.awt.Choice choUsertype;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel panAccountManagement;
    private javax.swing.JTable tblUsernamesTemp;
    // End of variables declaration//GEN-END:variables
    
}
