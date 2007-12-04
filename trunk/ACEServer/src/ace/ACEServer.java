package ace;
import java.io.*;
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
    Thread m_interestRateThread;
    InterestRateCalculator m_interestRateCalc;
    
    // constructor
    public ACEServer() {
    }
    
    // OS entry point
    public static void main(String[] args) {
        boolean doshutdown = false;
        String command = "";
        
        ACEServer s = new ACEServer();
        
        // Startup the system
        if (s.startup()) {
            System.out.println("All systems ok!");
            
            // Wait until an operator requests the server to shutdown
            /*try{
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                String str;
                while (!doshutdown && ((str = stdin.readLine()) != null) && (str.length() != 0)) {
                    if(str.equals("shutdown")) {
                        doshutdown = true;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }*/
            while(!doshutdown) {}
            
            System.out.println("System is shutting down...");
            s.shutdown();
            System.exit(0);
        } else {
            System.out.println("System could not initialize.");
        }
        
        System.exit(1);
    }

    /**
     * starts the server
     *
     * Returns true when successful and false otherwise
     */
    public boolean startup() {
        // Connect to the database
        m_db = new DBConnection();
        m_db.connect("localhost",5432);
        
        // Start listening to connections
        m_sni = new ServerNetworkInterface(m_db);
        m_sni.startListening(1234); // This function creates a new thread and returns when it is started.

        m_interestRateCalc = new InterestRateCalculator(m_db);
        m_interestRateThread = new Thread(m_interestRateCalc);
        m_interestRateThread.start();
        
        System.out.println("ACEServer is up!");
        return true;
    }
    

    /**
     * shuts the server down
     *
     * Returns true when successful and false otherwise
     */
    public void shutdown() {
        System.out.println("Shutting down the Interest Rate Calculator...");
        if (m_interestRateThread != null) {
            m_interestRateThread.interrupt();
        }
        m_interestRateCalc.stopMe();
        if (m_interestRateThread != null) {
            m_interestRateThread.run();
        }
        
        if(m_sni != null) {
            System.out.println("Closing all client connections...");
            // Close all client connections
            m_sni.closeAllClientConnections();
            // Stop listening
            System.out.println("Shutting down the network interface...");
            m_sni.stopListening();
            m_sni = null;
        }
        
        // Close connections to DB
        if(m_db != null) {
            System.out.println("Closing the connection to the database...");
            m_db.disconnect();
            m_db = null;
        }
        
        System.out.println("ACEServer is now down!");
        
        return;
    }    
    
}
