/*
 * AdminParser.java
 *
 * Created on November 23, 2007, 12:27 AM
 *
 */

package snetworking;

import fundamentals.*;
import database.*;

/**
 *
 * @author GLL
 */
public class AdminParser {
    DBConnection m_db;
    int m_userID;
    
    /** Creates a new instance of AdminParser */
    public AdminParser(DBConnection db, int userID) {
        m_db = db;
        m_userID = userID;
    }
    
    // Parse a string command sent by an admin user
    public String parseCommand(String command) {
        String[] args;
        EndUser user;
        
        args = command.split(" ");
        
        if(args.length <= 0) {
            return "error";
        } else if(args[0].equals("getcurrencies") && args.length == 1) {
            return "ok " + args[0] + " USD CAN YEN";
        } else if(args[0].equals("changepassword") && args.length == 2) {
            user = new EndUser(m_db, m_userID);
            user.setPassword(args[1]);
            return "ok changepassword";
        } else if(args[0].equals("createaccount") && args.length == 3) {
            user = new EndUser(m_db, args[1], args[2]);
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        }
        
        // Invalid command
        return "error " + args[0];
    }
}
