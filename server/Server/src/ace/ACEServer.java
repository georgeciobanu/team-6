package ace;

import database.*;
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
    
    // constructor
    public ACEServer() {
    }
        
    // OS entry point
    public static void main(String[] args) {
        
        ACEServer s = new ACEServer();
        
        if (s.startup()) {
            System.out.println("All systems ok!");
        }
        
        // TODO
        //    - here wait for any threads to finish...
        
        
        System.out.println("System is shutting down...");
        s.shutdown();
        
    }
    
    /**
     * starts the server
     *
     * Returns true when successful and false otherwise
     */
    public boolean startup() {

        
        m_sni = new ServerNetworkInterface(m_db);
        
        // TODO:
        //    - in a separate thread run the part that 
        //      listens to the sockets
        
        
        // Connect to the database
        m_db.connect("localhost",5432);

        // Start listening to connections
        m_sni.startListening(1234); // This function creates a new thread and returns when it is started.
        
        System.out.println("ACEServer is up!");
        return true;
    }
    

    /**
     * shuts the server down
     *
     * Returns true when successful and false otherwise
     */
    public void shutdown() {

        // Close all client connections
        if(m_sni != null) {
            m_sni.stopListening();
        }
        
        // Stop listening
        
        // Close connections to DB
        
        System.out.println("ACEServer is now down!");
                
    }    
    
}
