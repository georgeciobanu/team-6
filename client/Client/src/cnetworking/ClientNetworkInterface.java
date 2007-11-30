/*
 * ClientNetworkInterface.java
 *
 * Created on November 23, 2007, 12:10 AM
 *
 */

package cnetworking;

/**
 *
 * @author GLL
 */
public class ClientNetworkInterface {
    String m_serverName;  // The server name to connect to, in this project we will use localhost or 127.0.0.1
    int m_serverPort;     // The server port to connect to, in this project we will use 12345
    boolean m_connected;  // Saves whether the client app is connected to the server or not
    
    /** Creates a new instance of ClientNetworkInterface */
    public ClientNetworkInterface() {
        m_connected = false;
    }
    
    public boolean connect(String server, int port) {
        return false;
    }
    
    public boolean asyncSend(String message) {
        if(m_connected) {
            // Send a message to the server
        }
        
        // Could not send a message because the interface is not connected
        return false;
    }
    
    public boolean asyncReceive(String message) {
        if(m_connected) {
            // Receive a message
        }
        
        // Could not receive a message because the interface is not connected
        return false;
    }
    
    public boolean disconnect() {
        if(m_connected) {
            // Disconnect
        }
        
        // Could not disconnect because the interface is not connected
        return false;
    }
}
