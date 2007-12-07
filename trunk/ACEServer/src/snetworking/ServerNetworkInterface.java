/*
 * ServerNetworkInterface.java
 *
 * Created on November 23, 2007, 12:05 AM
 */


/**
 * 
 * @author Gabriel Lemonde-Labrecque
 */

package snetworking;

import database.*;

public class ServerNetworkInterface {
    private int m_port = -1;
    private int m_numberOfClientConnections = 0;
    private Thread m_thread;
    private DBConnection m_db;
    
    /** Creates a new instance of ServerNetworkInterface */
    public ServerNetworkInterface(DBConnection db) {
        m_db = db;
    }
    
    // Accept new end-user connections
    public boolean startListening(int port) {
            m_port = port;
            
            try {
                Runnable listener = new ServerListener(m_db, port);
                m_thread = new Thread(listener);
                m_thread.start();
                return true;
            } catch (Exception e) {
                return false;
            }
    }
    
    public boolean closeAllClientConnections() {
        // Wait for all client connections to be closed
        //while(m_sni.getNumberOfClientConnections() > 0){}
        return false;
    }
    
    // Set the accepting connection port
    public boolean stopListening() {
        return false;
    }
    
    // Set the accepting connection port
    public int getNumberOfClientConnections() {
        return m_numberOfClientConnections;
    }
}

