/*
 * LimitOrderPanel.java
 *
 * Created on October 21, 2007, 7:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ace;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Administrator
 */
public class LimitOrderPanel extends JPanel {
    
    /** Creates a new instance of LimitOrderPanel */
    public LimitOrderPanel() {
        setLayout( new BoxLayout ( this, BoxLayout.PAGE_AXIS ) );  
        setBorder( BorderFactory.createEtchedBorder ( EtchedBorder.RAISED ) );
        setMaximumSize( new Dimension (160, 120) );
        setPreferredSize( new Dimension (160, 120) );
        
        // label panel
        JPanel lp = new JPanel();
        lp.setLayout( new BoxLayout ( lp, BoxLayout.LINE_AXIS ) );
        JLabel l1 = new JLabel(" Limit Order");
        l1.setForeground( Color.CYAN );        
        lp.setBackground( Color.BLUE );
        lp.add( Box.createHorizontalGlue() );
        lp.add ( l1 );
        lp.add( Box.createHorizontalGlue() );
        
        // currency panel
        JPanel cp = new JPanel();
        cp.setLayout( new BoxLayout ( cp, BoxLayout.LINE_AXIS ) );
        String[] clist = { "USD/CAD", "USD/EUR", "USD/CHF", "EUR/CAD", "EUR/USD", "EUR/CHF" };
        JComboBox clc = new JComboBox(clist);
        clc.setMaximumSize( new Dimension ( 100, 20 ) );
        cp.add( Box.createRigidArea(new Dimension(5,0)) );
        cp.add( new JLabel("Currency:") );
        cp.add( Box.createRigidArea(new Dimension(5,0)) );
        cp.add( clc );        
        cp.add( Box.createRigidArea(new Dimension(5,0)) );
        
        // amount panel
        JPanel ap = new JPanel();
        ap.setLayout( new BoxLayout ( ap, BoxLayout.LINE_AXIS ) );
        JSpinner as = new JSpinner( new SpinnerNumberModel(100.0, 0.0, 10000.0, 100.0) );        
        as.setMaximumSize( new Dimension ( 100, 20 ) );
        ap.add( Box.createRigidArea(new Dimension(5,0)) );
        ap.add( new JLabel("Amount:") );  
        ap.add( Box.createHorizontalGlue() );
        ap.add( as );
        ap.add( Box.createRigidArea(new Dimension(5,0)) );
        
        // option panel
        JPanel op = new JPanel();
        op.setLayout( new BoxLayout ( op, BoxLayout.LINE_AXIS ) );
        JSpinner ls = new JSpinner( new SpinnerNumberModel(1.08, 0.0, 3.0, 0.01) );
        ls.setMaximumSize( new Dimension ( 100, 20 ) );
        ls.setPreferredSize( new Dimension ( 45, 20 ) );
        String[] ol = { "Limit Order", "Stop Loss" };
        JComboBox ocl = new JComboBox ( ol );
        ocl.setMaximumSize( new Dimension ( 100, 20 ) );
        op.add( Box.createRigidArea(new Dimension(5,0)) );
        op.add( ocl );  
        op.add( Box.createHorizontalGlue() );
        op.add( ls );
        op.add( Box.createRigidArea(new Dimension(5,0)) );
        
        // button panel
        JPanel bp = new JPanel();
        bp.setLayout( new BoxLayout ( bp, BoxLayout.LINE_AXIS ) );  
        //bp.setBackground( Color.GRAY );        
        JButton bb = new JButton("Buy");
        bb.setPreferredSize( new Dimension( 60, 60));
        JButton sb = new JButton("Sell");
        sb.setPreferredSize( new Dimension( 100, 60));
        bp.add( Box.createRigidArea(new Dimension(5,0)) );
        bp.add ( bb );        
        bp.add( Box.createRigidArea(new Dimension(5,0)) );        
        bp.add ( sb );
        bp.add( Box.createRigidArea(new Dimension(5,0)) );
        

        
        add ( lp );        
        add ( cp );        
        add ( ap );
        add ( op );
        add ( bp );
        add ( Box.createVerticalGlue() );
    }
    
}
