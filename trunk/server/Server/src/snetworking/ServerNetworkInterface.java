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

import java.net.*;
import java.io.*;
import java.util.*;

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
        
        try{
            ServerSocket socket1 = new ServerSocket(port);
            m_isAccepting = true;
            System.out.println("ServerNetworkInterface Initialized");
            while (true) {
                Socket connection = socket1.accept();
                Runnable runnable = new MultipleSocketServer(m_db, connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
            //return true;
        } catch (Exception e) {
            System.out.println("ServerNetworkInterface Exception: " + e.getMessage());
            return false;
        }
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

