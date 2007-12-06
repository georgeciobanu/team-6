/*
 * AdminParser.java
 *
 * Created on November 23, 2007, 12:27 AM
 *
 */

package snetworking;

import sFundamentals.*;
import database.*;
import transactionEngine.*;
import java.util.Vector;
import java.util.Enumeration;

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
        sEndUser user;
        Market market;
        
        args = command.split(" ");
        
        if(args.length <= 0) {
            return "error";
        } else if(args[0].equals("getcurrencies") && args.length == 1) {
            String ret = "";
            market = new Market(m_db);
            String[] currenciesList = market.getCurrencies();
            if(currenciesList.length < 1) {
                return "error getcurrencies";
            } else if(currenciesList[0].equals("")) {
                return "error getcurrencies";
            }
            for(int i = 0; i < currenciesList.length; i++) {
                ret += currenciesList[i] + " ";
            }
            return "ok getcurrencies " + ret.trim();
        } else if(args[0].equals("changepassword") && args.length == 2) {
            user = new sEndUser(m_db, m_userID);
            user.setPassword(args[1]);
            return "ok changepassword";
        } else if(args[0].equals("createaccount") && args.length == 3) {
            user = new sEndUser(m_db, -1);
            if(user.createAccount(args[1], args[2]) != -1) {
                return "ok createaccount " + args[1];
            }
            return "error createaccount " + args[1];
        } else if(args[0].equals("getusernames") && args.length == 2) {
            String ret = "";
            Vector<String> v = null;
            
            if(args[1].equals("0")) {
                v = m_db.getUsernames(DBConnection.USERSTATUS.ENDUSER);
            } else if(args[1].equals("1")) {
                v = m_db.getUsernames(DBConnection.USERSTATUS.ADMINISTRATOR);
            } else {
                return "error getusernames 1";
            }
            
            if(v != null) {
                for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
                    ret += (String)e.nextElement() + " ";
                }
                return "ok getusernames " + ret;
            } else {
                return "error getusernames 2";
            }
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
