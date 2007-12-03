package gui;
/*
 * ChangePassword.java
 *
 * Created on November 20, 2007, 8:46 PM
 */

/**
 *
 * @author  kselik
 */

import javax.swing.*;
import clientnetworking.*;
public class ChangePassword extends javax.swing.JPanel {
    JFrame owner;
    JPanel tab;
    ClientNetworkInterface m_cni;
    
    /** Creates new form ChangePassword */
    public ChangePassword(JPanel tab, JFrame owner, ClientNetworkInterface cni) {
        this.owner=owner;
        this.tab=tab;
        m_cni = cni;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        pwdPassword1 = new javax.swing.JPasswordField();
        pwdPassword2 = new javax.swing.JPasswordField();
        btnCancel = new javax.swing.JButton();

        jLabel2.setText("Password:");

        jLabel3.setText("Password again:");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmit))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pwdPassword2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(pwdPassword1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))
                .addGap(161, 161, 161))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pwdPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pwdPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnCancel))
                .addContainerGap(168, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        // Go to UserMenu
        tab.setVisible(true);
        owner.setContentPane(tab);
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        String password1 = pwdPassword1.getText();
        String password2 = pwdPassword2.getText();
        
        if(password1.equals(password2) && password1.indexOf(" ") < 0){
            String message;
            
            // Verify that the admin is still connected
            if(m_cni.isConnected()) {
                // Send request
                m_cni.SendMessage("changepassword " + password1);
                while((message = m_cni.ReceiveMessage()).equals("") && m_cni.isConnected());
                
                if(message.equals("ok changepassword")) {
                    // Go to UserMenu
                    tab.setVisible(true);
                    owner.setContentPane(tab);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Change password request failed.");
                }
            } else {
                // Error message
                JOptionPane.showMessageDialog(new JFrame(), "You are not connected to the server.");
            }
        } else {
            // Error message
            JOptionPane.showMessageDialog(new JFrame(), "The password are not identical. Please enter two identical passwords.");
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField pwdPassword1;
    private javax.swing.JPasswordField pwdPassword2;
    // End of variables declaration//GEN-END:variables
    
}
