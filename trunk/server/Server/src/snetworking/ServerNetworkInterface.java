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

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerNetworkInterface {
    boolean m_isAccepting;
    int m_port = 12345;
    
    /** Creates a new instance of ServerNetworkInterface */
    public ServerNetworkInterface() {
        m_isAccepting = false;
    }
    // Accept new end-user connections
    /*
     * TODO: Make this function asynchronous
     *
     */
    public boolean asyncListen(int port) {
        int count = 0;
        m_port = port;
        
        try{
            ServerSocket socket1 = new ServerSocket(port);
            m_isAccepting = true;
            System.out.println("ServerNetworkInterface Initialized");
            while (true) {
                Socket connection = socket1.accept();
                Runnable runnable = new MultipleSocketServer(connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
            //return true;
        } catch (Exception e) {
            return false;
        }
    }
    /*
    // Get the accepting connection port
    public int getPort() {
        return m_port;
    }
    
    // Set the accepting connection port
    public boolean setPort(int port) {
        if (m_isAccepting) {
            m_port = port;
        }
        return false;
    }
    
    // Send a message to a client application
    public boolean asyncSend(int userID, String message) {
        return false;
    }
    
    // Receive messages from a client application
    public boolean asyncReceive(String buffer) {
        return false;
    }
     */
}

