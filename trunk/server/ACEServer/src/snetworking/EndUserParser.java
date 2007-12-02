/*
 * EndUserParser.java
 *
 * Created on November 23, 2007, 12:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snetworking;

/**
 *
 * @author GLL
 */
public class EndUserParser {
    /** Creates a new instance of EndUserParser */
    public EndUserParser() {
        
    }
    
    // Parse a string command sent by an end-user
    public String parseCommand(String command) {
        String[] args;
        
        args = command.split(" ");
        
        if(args.length <= 0) {
            return "error" + (char) 13;
        } else if(args[0].equals("getcurrencies")) {
            
        } else if(args[0].equals("placemarketorder")) {
            
        } else if(args[0].equals("editmarketorder")) {
            
        } else if(args[0].equals("placelimitorder")) {
            
        } else if(args[0].equals("editlimitorder")) {
            
        } else if(args[0].equals("placestoporder")) {
            
        } else if(args[0].equals("editstoporder")) {
            
        } else if(args[0].equals("placetrailingstoporder")) {
            
        } else if(args[0].equals("edittrailingstoporder")) {
            
        } else if(args[0].equals("pricehistory")) {
            
        } else if(args[0].equals("pendingorder")) {
            
        } else if(args[0].equals("marketprice")) {
            
        }
        
        // Invalid command
        return "error " + args[0] + (char) 13;
    }
}
