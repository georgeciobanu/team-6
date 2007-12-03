package ace;

import database.*;
import javax.swing.Timer;
import snetworking.*;
/*
 * ACEServer.java
 *
 * Created on November 22, 2007, 8:16 PM
 *
 */

/**
 *
 * @author Alex Ciobanu
 */
public class ACEServer {
    DBConnection m_db;
    ServerNetworkInterface m_sni;
    Timer serverProcessor = new Timer();
    
    // constructor
    public ACEServer() {
    }
        
    // OS entry point
    public static void main(String[] args) {
        
        ACEServer s = new ACEServer();
        
        if (s.startup()) {
            System.out.println("All systems ok!");
        }

        System.out.println("System is shutting down...");
        s.shutdown();
    }
    
    /**
     * starts the server
     *
     * Returns true when successful and false otherwise
     */
    public boolean startup() {        
        boolean doshutdown = false;
        
        
        
        // Connect to the database
        m_db = new DBConnection();
        m_db.connect("localhost",5432);
        
        // Start listening to connections
        m_sni = new ServerNetworkInterface(m_db);
        m_sni.startListening(1234); // This function creates a new thread and returns when it is started.

        // Wait till we want to shutdown
        while(!doshutdown) {}
        
        System.out.println("ACEServer is up!");
        return true;
    }
    

    /**
     * shuts the server down
     *
     * Returns true when successful and false otherwise
     */
    public void shutdown() {
        if(m_sni != null) {
            // Close all client connections
            m_sni.closeAllClientConnections();
            // Stop listening
            m_sni.stopListening();
        }
        
        // Close connections to DB
        if(m_db != null) {
            m_db.disconnect();
        }
        
        System.out.println("ACEServer is now down!");
                
    }    
    
}
