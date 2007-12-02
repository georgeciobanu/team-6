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
    boolean m_isAccepting;
    int m_port;
    int m_numberOfClientConnections = 0;
    DBConnection m_db;
    
    /** Creates a new instance of ServerNetworkInterface */
    public ServerNetworkInterface(DBConnection db) {
        m_isAccepting = false;
        m_db = db;
    }
    // Accept new end-user connections
    /*
     * TODO: Make this function asynchronous
     *
     */
    public boolean startListening(int port) {
            int count = 0;
            m_port = port;
            m_isAccepting = true;

            Runnable listener = new ServerListener(m_db, port);
            Thread thread = new Thread(listener);
            thread.start();
            return true;
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

