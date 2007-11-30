/*
 * MarketPanel.java
 *
 * Created on October 21, 2007, 5:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ace;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Alex Ciobanu
 */
public class MarketPanel extends JPanel {
    
    /** Creates a new instance of MarketPanel */
    public MarketPanel() {
        //setBackground( Color.blue );
        
        JTabbedPane ltp = new JTabbedPane();        
        ltp.add("Chart", new ChartPanel());        
        ltp.add("Market", new ActivityPanel());        
        
        add( ltp );
    }
    
}
