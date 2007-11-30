/*
 * ActivityPanel.java
 *
 * Created on October 21, 2007, 6:48 PM
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
import java.text.*;

/**
 *
 * @author Alex & George
 */
public class ActivityPanel extends JPanel {
    
    /** Creates a new instance of ActivityPanel */
    public ActivityPanel() {        
        setLayout( new BoxLayout ( this, BoxLayout.PAGE_AXIS ) );  
        
        
        //market status table
        String DATE_FORMAT = "HH:mm:ss";
                
        //get the current time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String ntime = sdf.format(cal.getTime());
        
        String [] TableColumns = {
            "Currency", "Bid", "Ask", "High", "Low", "Time"
        };
        
        Object [][] Data = {
            {"EUR/USD", new Double(1.42991), new Double(1.43021), new Double(1.43021), new Double(1.42991), ntime}
        };
        
        JTable tb = new JTable(Data, TableColumns);        
        tb.setPreferredScrollableViewportSize( new Dimension(100, 50) );

        JPanel atp = new JPanel();
        atp.setLayout( new BoxLayout ( atp, BoxLayout.LINE_AXIS ) );  
        atp.add( Box.createRigidArea(new Dimension(5,0)) );  
        atp.add ( new JScrollPane ( tb ) );        
        atp.add( Box.createRigidArea(new Dimension(5,0)) );  
        
        add ( atp );
        add ( Box.createVerticalGlue() );
        
    }
    
}
