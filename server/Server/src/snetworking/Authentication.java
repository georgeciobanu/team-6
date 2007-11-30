/*
 * Authentication.java
 *
 * Created on November 23, 2007, 12:26 AM
 *
 */

package snetworking;

/**
 *
 * @author GLL
 */
public class Authentication {
    private enum USERSTATUS {
        NOTAUTHENTICATED,
        ADMINISTRATOR,
        ENDUSER
    }
    
    USERSTATUS m_userstatus;
    EndUserParser eup = new EndUserParser();
    AdminParser ap = new AdminParser();
    
    /** Creates a new instance of Authentication */
    public Authentication() {
        m_userstatus = USERSTATUS.NOTAUTHENTICATED;
    }
    
    // Preparse a command received from the network
    public boolean parseCommand(String command) {
        // Check if user has proper rights to access the server
        if(m_userstatus != USERSTATUS.NOTAUTHENTICATED)
        {
            // Check if the received command is a logout command
            if(command.startsWith("login ")) {
                
            }
            return false;
        } else if(m_userstatus == USERSTATUS.ADMINISTRATOR) {
            // Redirect parsing to the end-user's parser
            ap.parseCommand(command);
        } else if(m_userstatus == USERSTATUS.ENDUSER) {
            // Redirect parsing to the administrator's parser
            eup.parseCommand(command);
        }
    }
}