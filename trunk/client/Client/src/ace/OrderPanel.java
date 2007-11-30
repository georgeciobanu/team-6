/*
 * OrderPanel.java
 *
 * Created on October 21, 2007, 6:26 PM
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
 * @author Alex Ciobanu
 */
public class OrderPanel extends JPanel {
    
    /** Creates a new instance of OrderPanel */
    public OrderPanel() {
        setLayout( new BoxLayout ( this, BoxLayout.LINE_AXIS ) );
        
        add ( new TransactionPanel ( "USD", "JPY", 114.496, 114.541 ) );
        add ( new TransactionPanel ( "EUR", "CAD", 1.38192, 1.38277 ) );        
        add ( Box.createHorizontalGlue() );
        add ( new TStopOrderPanel ( ) );
        add ( new LimitOrderPanel ( ) );
    }
    
}
