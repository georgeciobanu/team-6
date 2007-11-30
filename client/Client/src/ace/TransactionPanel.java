/*
 * TransactionPanel.java
 *
 * Created on October 21, 2007, 12:47 PM
 *
 */

package ace;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;


/**
 * @author Alex Ciobanu
 */
public class TransactionPanel extends JPanel {
    
    /** Creates a new instance of TransactionPanel */
    public TransactionPanel( String cur1, String cur2, double price1, double price2 ) {
        setLayout( new BoxLayout ( this, BoxLayout.PAGE_AXIS ) );  
        setBorder( BorderFactory.createEtchedBorder ( EtchedBorder.RAISED ) );
        setMaximumSize( new Dimension (160, 120) );
        setPreferredSize( new Dimension (160, 120) );
        
        // label panel
        JPanel lp = new JPanel();
        lp.setLayout( new BoxLayout ( lp, BoxLayout.LINE_AXIS ) );
        JLabel l1 = new JLabel(cur1 + "/" + cur2);
        l1.setForeground( Color.CYAN );        
        lp.setBackground( Color.BLUE );
        lp.add( Box.createHorizontalGlue() );
        lp.add ( l1 );
        lp.add( Box.createHorizontalGlue() );
        
        // price panel
        JPanel pp = new JPanel();
        pp.setLayout( new BoxLayout ( pp, BoxLayout.LINE_AXIS ) );
        JLabel l2 = new JLabel(price1 + "/" + price2);
        l2.setForeground( Color.WHITE );
        pp.setBackground( Color.BLACK );
        pp.add( Box.createHorizontalGlue() );
        pp.add ( l2 );
        pp.add( Box.createHorizontalGlue() );

        // amount panel
        JPanel ap = new JPanel();
        ap.setLayout( new BoxLayout ( ap, BoxLayout.LINE_AXIS ) );
        JSpinner as = new JSpinner( new SpinnerNumberModel(100.0, 0.0, 10000.0, 100.0) );        
        ap.add( Box.createRigidArea(new Dimension(5,0)) );
        ap.add( new JLabel("Amount:") );  
        ap.add( Box.createHorizontalGlue() );
        ap.add( as );
        ap.add( Box.createRigidArea(new Dimension(5,0)) );   
        
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
        add ( pp );
        add( Box.createRigidArea(new Dimension(0,2)) );
        add ( ap );
        add ( bp );
        add ( Box.createVerticalGlue() );
    }
    
}
