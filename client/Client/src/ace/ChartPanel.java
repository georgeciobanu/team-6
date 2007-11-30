/*
 * ChartPanel.java
 *
 * Created on October 21, 2007, 3:33 PM
 */

package ace;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author  Gabriel Lemonde-Labrecque
 */
public class ChartPanel extends JPanel {
        
    public ChartPanel() {       
        setLayout(new BoxLayout( this, BoxLayout.PAGE_AXIS));
        
        // currecy list panel
        JPanel clp = new JPanel();
        clp.setLayout(new BoxLayout( clp, BoxLayout.LINE_AXIS));
        String[] clist = { "USD/CAD", "USD/EUR", "USD/CHF", "EUR/CAD", "EUR/USD", "EUR/CHF" };
        JComboBox clc = new JComboBox(clist);
        clc.setMaximumSize( new Dimension ( 100, 20 ) );
        clp.add( Box.createRigidArea(new Dimension(5,0)) );
        clp.add( new JLabel("Currency:") );
        clp.add( Box.createRigidArea(new Dimension(5,0)) );
        clp.add( clc );
        
        // image panel
        JPanel ip = new JPanel();
        ip.setLayout(new BoxLayout( ip, BoxLayout.LINE_AXIS));        
        ip.add ( new JLabel(new ImageIcon(getClass().getResource("/ace/graph.png"))) );                
        
        add( Box.createRigidArea(new Dimension(0,3)) );
        add ( clp );
        add( Box.createRigidArea(new Dimension(0,3)) );
        add ( ip );
        
        
    }
}
