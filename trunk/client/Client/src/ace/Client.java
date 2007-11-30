package ace;
/*
 * Client.java
 *
 * Created on October 21, 2007, 12:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.asdasd
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Client extends JFrame {
    
    private JPanel top_panel, middle_panel, bottom_panel, left_panel;
       
    public Client() {                
        // setup the GUI
        initGUI();        
        pack();
    }    
    
    // initializes the GUI
    private void initGUI() {
        setTitle("ACE System Client v0.1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize( new Dimension(800, 535));
        
        // set-up main panel
        JPanel main_panel = (JPanel) getContentPane();
        main_panel.setLayout(new BoxLayout( main_panel, BoxLayout.PAGE_AXIS));
        
        
        // top panel         
        top_panel = new JPanel();
        top_panel.add ( new OrderPanel() );
        top_panel.setLayout( new BoxLayout ( top_panel, BoxLayout.LINE_AXIS ) );                
                
        // middle panel
        middle_panel = new JPanel();
        middle_panel.setLayout( new BoxLayout ( middle_panel, BoxLayout.LINE_AXIS ) );        
        middle_panel.add ( new MarketPanel() );
        middle_panel.add ( new AccountPanel() );
        
        // bottom panel
        bottom_panel = new JPanel();
        bottom_panel.setLayout( new BoxLayout ( bottom_panel, BoxLayout.LINE_AXIS ) );
        bottom_panel.add( Box.createVerticalGlue() );
        //bottom_panel.add ( new PositionPanel() );
       
        
        main_panel.add ( top_panel );
        main_panel.add ( middle_panel );
        main_panel.add ( bottom_panel );
        //main_panel.add ( Box.createVerticalGlue() );
        
    }
    
    
    public static void main(String[] args) {
        new Client().setVisible(true);
    }
                
}
