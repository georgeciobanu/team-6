/*
 * ClientNetworkInterface.java
 *
 * Created on December 1, 2007, 12:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package clientnetworking;

/* This code was found on http://dn.codegear.com/article/31995 */
/*  The java.net package contains the basics needed for network operations. */
import java.net.*;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;
import java.util.*;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */

/**
 *
 * @author GLL
 */
public class ClientNetworkInterface {
    boolean m_connected = false;
    String m_server;
    int m_port;
    Socket m_connection;
    BufferedOutputStream m_bos;
    OutputStreamWriter m_osw;
    BufferedInputStream m_bis;
    InputStreamReader m_isr;
    StringBuffer instr;
    
    /** Creates a new instance of ClientNetworkInterface */
    public ClientNetworkInterface() {
    }
    
    public boolean connect(String server, int port) {
        if(!isConnected()) {
            System.out.println("ClientNetworkInterface initialized");
            
            try {
                /** Obtain an address object of the server */
                InetAddress address = InetAddress.getByName(server);
                /** Establish a socket connetion */
                m_connection = new Socket(address, port);
                /** Instantiate a BufferedOutputStream object */
                
                //m_connection
                
                instr = new StringBuffer();
                
                m_bos = new BufferedOutputStream(m_connection.getOutputStream());
                m_osw = new OutputStreamWriter(m_bos, "US-ASCII");
                m_bis = new BufferedInputStream(m_connection.getInputStream());
                m_isr = new InputStreamReader(m_bis, "US-ASCII");
                
                m_connected = true;
                return true;
            } catch (IOException f) {
                System.out.println("Connection to the server failed!");
                m_connected = false;
                return false;
            } catch (Exception g) {
                System.out.println("Exception: " + g);
                disconnect();
            }
        }
        
        return true;
    }
    
    public boolean SendMessage(String message) {
        if(m_connected) {
            /** Write across the socket connection and flush the buffer */
            try {
                m_osw.write(message + (char)13);
                m_osw.flush();
            } catch (IOException f) {
                System.out.println("IOException: " + f);
                m_connected = false;
            } catch (Exception g) {
                System.out.println("Exception: " + g);
                m_connected = false;
            }
        }
        
        return false;
    }
    
    public String ReceiveMessage() {
        String returnMessage = "";
        try {
            System.out.println("1:");
            if(m_connected) {
                int c;
                System.out.println("2:");
                while(((c = m_isr.read()) != 13) && (c != -1)) {
                    instr.append((char) c);
                }
                if(c == -1) return "";
                
                System.out.println("3:");
                System.out.println(instr);
                
                System.out.println("4:");
                returnMessage = instr.toString();
                
                // Clear buffer
                System.out.println("5:");
                instr.delete(0,instr.length());
            }
            return returnMessage;
        } catch (IOException f) {
            System.out.println("IOException: " + f);
            disconnect();
            return "";
        } catch (Exception g) {
            System.out.println("here:");
            System.out.println("Exception: " + g);
            disconnect();
            return returnMessage;
        }
    }
    
    /*public boolean newReceivedMessage() {
        return false;
    }*/
    
    public boolean isConnected() {
        try {
            if(m_connection == null) {
                m_connected = false;
            } else {
                m_connected = m_connection.isConnected() && !m_connection.isClosed();
            }
            return m_connected;
            
        } catch(Exception e) {
            e.printStackTrace();
            return m_connected = false;
        }
    }
    
    public boolean disconnect() {
        try {
            if(isConnected())
            {
                /** Close the socket connection. */
                //m_osw.close();
                m_bos.close();
                //m_isr.close();
                m_bis.close();
                m_connection.close();
                System.out.println("You are now disconnected.");
            }
            m_connected = false;
        } catch (IOException f) {
            System.out.println("IOException: " + f);
            m_connected = false;
        } catch (Exception g) {
            System.out.println("Exception: " + g);
            m_connected = false;
        }
        return true;
    }
}
