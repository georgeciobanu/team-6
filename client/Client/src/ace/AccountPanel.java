/*
 * kpanel.java
 *
 * Created on October 21, 2007, 1:45 PM
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
 * @author icioba1
 */
public class AccountPanel extends JPanel {
    
    /** Creates a new instance of kpanel */
    public AccountPanel() {
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(700, 110));
        //setBackground(Color.RED);
        
        JPanel bpanel= new JPanel();
        JPanel blotter= new JPanel();
        JPanel lpanel= new JPanel();
        JPanel rpanel= new JPanel();
        
        JPanel billing=new JPanel();
        billing.setLayout(new BoxLayout(billing,BoxLayout.PAGE_AXIS));
        
        JTextArea billarea= new JTextArea("123");
        billing.add( new JLabel("Latest Bills:"));
        
         String [] TableColumns = {
            "Currency", "Bid"
        };
        
        Object [][] Data = {
            {"January", "200$"},
            {"September", "300$"},
            {"November", "500$"}
      
        };
        
        JTable tb = new JTable(Data, TableColumns);        
        //tb.setPreferredScrollableViewportSize( new Dimension(100, 50) );
        
        billing.add(tb);
        
        JPanel loginpanel=new JPanel();       
        loginpanel.setLayout(new BoxLayout(loginpanel,BoxLayout.PAGE_AXIS)); 
     
        JTextField userid= new JTextField();
        userid.setMaximumSize( new Dimension(80, 20) );
        userid.setPreferredSize( new Dimension(80, 20) );
        JPasswordField password = new JPasswordField();
        password.setMaximumSize( new Dimension(80, 20) );
        password.setPreferredSize( new Dimension(80, 20) );
        JButton loginbutton= new JButton("Login");
        
        JPanel toploginpanel= new JPanel();
        toploginpanel.setLayout(new BoxLayout(toploginpanel,BoxLayout.LINE_AXIS));
       
        toploginpanel.add( Box.createRigidArea(new Dimension(60,0)) ); 
        loginpanel.add( Box.createRigidArea(new Dimension(0,15)) ); 
        toploginpanel.add(  new JLabel("User ID") );
        toploginpanel.add( Box.createHorizontalGlue() );
        toploginpanel.add(userid);
        toploginpanel.add( Box.createRigidArea(new Dimension(120,0)) );   
        
        JPanel middleloginpanel= new JPanel();
        middleloginpanel.setLayout(new BoxLayout(middleloginpanel,BoxLayout.LINE_AXIS));
        middleloginpanel.add( Box.createRigidArea(new Dimension(60,0)) );   
        middleloginpanel.add( new JLabel("Password") );
        middleloginpanel.add( Box.createHorizontalGlue() );
        middleloginpanel.add(password);
        middleloginpanel.add( Box.createRigidArea(new Dimension(120,0)) );   
        
        loginpanel.add(toploginpanel);
        loginpanel.add(middleloginpanel); 
        loginpanel.add( Box.createRigidArea(new Dimension(0,10)) ); 
        loginpanel.add(loginbutton);
        loginpanel.add ( Box.createVerticalGlue() );

     
        JTextArea text= new JTextArea();
        JLabel actlog=new JLabel("Activity Log");
        
        text.setText("(16:24:46)Accepted Order: 4645\n(17:34:36)Accepted Order: 9284 \n(18:15:36)Accepted Order: 9398\n(19:14:49)Accepted Order: 8498\n");
        rpanel.add(actlog);   
        rpanel.add(text);
        
        
        
        rpanel.setLayout(new BoxLayout(rpanel,BoxLayout.PAGE_AXIS));
        lpanel.setLayout(new BoxLayout(lpanel,BoxLayout.PAGE_AXIS));
        bpanel.setLayout(new BoxLayout(bpanel,BoxLayout.LINE_AXIS));
        //blotter.setLayout(new BoxLayout(blotter,BoxLayout.LINE_AXIS));
        
        JPanel spp = new JPanel();
        spp.setLayout(new BoxLayout(spp,BoxLayout.LINE_AXIS));
                          
        ScrollPane l= new ScrollPane();
        
        ScrollPane r= new ScrollPane();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, l, r);    
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(260);        
        l.setMinimumSize( new Dimension(100, 50));
        r.setMinimumSize( new Dimension(100, 50));
        spp.add( splitPane );

              
       
         
          JPanel p1= new JPanel();
          p1.setLayout(new BoxLayout(p1,BoxLayout.LINE_AXIS));
          p1.add(new JLabel("Account Balance"));
          p1.add( Box.createHorizontalGlue() );
          p1.add(new JLabel("$ 10.00"));
         
         JPanel p2= new JPanel();
         p2.setLayout(new BoxLayout(p2,BoxLayout.LINE_AXIS));
         p2.add(new JLabel("Realised Gain/Loss"));
         p2.add( Box.createHorizontalGlue() );
         p2.add(new JLabel("$ -7.55"));
         
         JPanel p3= new JPanel();
         p3.setLayout(new BoxLayout(p3,BoxLayout.LINE_AXIS));
         p3.add(new JLabel("Unrealised Gain/Loss"));
         p3.add( Box.createHorizontalGlue() );
         p3.add(new JLabel("$ 5.00"));
         
         JPanel p4= new JPanel();
         p4.setLayout(new BoxLayout(p4,BoxLayout.LINE_AXIS));
         p4.add(new JLabel("Margin Balance"));
         p4.add( Box.createHorizontalGlue() );
         p4.add(new JLabel("$ 10.00"));
         
      
         lpanel.add(p1);
         lpanel.add(p2);
         lpanel.add(p3);
         lpanel.add(p4);
  
        l.add(lpanel);
        r.add(rpanel);
      

        addAButton("Deal Analysis", bpanel);
        addAButton("Commentary", bpanel);
        addAButton("Research", bpanel);
        addAButton("Reports", bpanel);
        addAButton("Login", bpanel);
        
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Deal Analysis", splitPane );
        tabbedPane.addTab("Position", new PositionPanel() );
        tabbedPane.addTab("Billing", billing );
        tabbedPane.addTab("Login", loginpanel );
        
        
        add(Box.createHorizontalGlue());
        add(tabbedPane);
        
        
        
     
    }   
       private static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
      
        container.add(button);
      
    }
    
}
