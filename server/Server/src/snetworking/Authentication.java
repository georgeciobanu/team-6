/*
 * Authentication.java
 *
 * Created on November 23, 2007, 12:26 AM
 *
 */

package snetworking;

import database.*;

/**
 *
 * @author GLL
 */
public class Authentication {
    public static enum USERSTATUS {
        NOTAUTHENTICATED,
        ADMINISTRATOR,
        ENDUSER
    }
    
    DBConnection m_db;
    int m_userID = -1;
    
    USERSTATUS m_userstatus;
    EndUserParser eup = new EndUserParser();
    AdminParser ap = new AdminParser();
    
    /** Creates a new instance of Authentication */
    public Authentication(DBConnection db) {
        m_userstatus = USERSTATUS.NOTAUTHENTICATED;
        m_db = db;
    }
    
    // Preparse a command received from the network
    public String parseCommand(String command) {
        String[] args;
        int userID = -1;
        USERSTATUS usertype = USERSTATUS.NOTAUTHENTICATED;
        
        args = command.split(" ");

        if(args.length > 0) {
            // Check if user has proper rights to access the server
            if(m_userstatus == USERSTATUS.NOTAUTHENTICATED) {
                // Check if the received command is a login command
                if(args[0].equals("login")) {
                    
                    // Query the database for this user's data
                    //userID = m_db.query("select id from user where username=" + args[1] + " and password= " + args[2]);
                    //usertype = m_db.query("select usertype from user where username=" + args[1] + " and password= " + args[2]);
                    
                    // Authenticate
                    if(usertype == USERSTATUS.NOTAUTHENTICATED) {
                        return "error login";
                    }
                    
                    // Get user type
                    if(usertype.equals("administrator")) {
                        //m_userID = Integer.parseInt(userID);
                        m_userstatus = USERSTATUS.ADMINISTRATOR;
                        return "ok login";
                    } else if(usertype.equals("enduser")) {
                        //m_userID = Integer.parseInt(userID);
                        m_userstatus = USERSTATUS.ENDUSER;
                        return "ok login";
                    }
                }
                return "error login";
            }
            
            // Check if user wants to log out
            if(args[0].equals("logout")) {
                m_userID = -1;
                m_userstatus = USERSTATUS.NOTAUTHENTICATED;
                // Close connection
                // [Not implemented yet]
            }
            
            if(m_userstatus == USERSTATUS.ADMINISTRATOR) {
                // Redirect parsing to the end-user's parser
                return ap.parseCommand(command);
            } else if(m_userstatus == USERSTATUS.ENDUSER) {
                // Redirect parsing to the administrator's parser
                return eup.parseCommand(command);
            }
        }
        
        // Invalid command
        return "error";
    }
}