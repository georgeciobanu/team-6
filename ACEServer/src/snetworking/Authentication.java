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
    private DBConnection m_db;
    private int m_userID = -1;
    private DBConnection.USERSTATUS m_userstatus;
    private EndUserParser eup;
    private AdminParser ap;
    
    /** Creates a new instance of Authentication */
    public Authentication(DBConnection db) {
        m_userstatus = DBConnection.USERSTATUS.NOTAUTHENTICATED;
        m_db = db;
    }
    
    // Preparse a command received from the network
    public String parseCommand(String command) {
        String[] args ;
        int userID = -1;
        DBConnection.USERSTATUS usertype = DBConnection.USERSTATUS.NOTAUTHENTICATED;
        
        args = command.split(" ");

        if(args.length > 0) {
            // Check if user has proper rights to access the server
            if(m_userstatus == DBConnection.USERSTATUS.NOTAUTHENTICATED) {
                // Check if the received command is a login command
                /*System.out.println("Command: " + args[0] + (char) 13);
                System.out.println("Arg1: " + args[1] + (char) 13);
                System.out.println("Arg2: " + args[2] + (char) 13);*/
                
                if(args[0].equals("login")) {
                    // Get User ID
                    if(args.length == 3) {
                        userID = m_db.getUserID(args[1],args[2]);
                        
                        if(userID != -1) {
                            // Get User Type
                            usertype = m_db.getUserType(userID);
                            
                            // Authenticate
                            if(usertype == DBConnection.USERSTATUS.NOTAUTHENTICATED) {
                                return "error login";
                            }
                            
                            // Get user type
                            if(usertype == DBConnection.USERSTATUS.ADMINISTRATOR) {
                                m_userstatus = usertype;
                                ap = new AdminParser(m_db, userID);
                                return "ok login administrator";
                            } else if(usertype == DBConnection.USERSTATUS.ENDUSER) {
                                m_userstatus = usertype;
                                eup = new EndUserParser(m_db, userID);
                                return "ok login enduser";
                            }
                        }
                    }
                }

                    //userID = m_db.query("select id from user where username=" + args[1] + " and password= " + args[2]);
                    //usertype = m_db.query("select usertype from user where username=" + args[1] + " and password= " + args[2]);
                    

                return "error login";
            }
            
            // Check if user wants to log out
            if(args[0].equals("logout")) {
                m_userID = -1;
                m_userstatus = DBConnection.USERSTATUS.NOTAUTHENTICATED;
                // Close connection
                return "logout";
            }
            
            if(m_userstatus == DBConnection.USERSTATUS.ADMINISTRATOR) {
                // Redirect parsing to the end-user's parser
                return ap.parseCommand(command);
            } else if(m_userstatus == DBConnection.USERSTATUS.ENDUSER) {
                // Redirect parsing to the administrator's parser
                return eup.parseCommand(command);
            }
        }
        
        // Invalid command
        return "error";
    }
}