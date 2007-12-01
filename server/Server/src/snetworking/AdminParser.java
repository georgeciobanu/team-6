/*
 * AdminParser.java
 *
 * Created on November 23, 2007, 12:27 AM
 *
 */

package snetworking;

/**
 *
 * @author GLL
 */
public class AdminParser {
    
    /** Creates a new instance of AdminParser */
    public AdminParser() {
    }
    
    // Parse a string command sent by an admin user
    public String parseCommand(String command) {
        String[] args;
        
        args = command.split(" ");
        
        if(args.length <= 0) {
            return "error" + (char) 13;
        } else if(args[0].equals("getcurrencies")) {
            return "ok " + args[0] + "USD CAN YEN" + (char) 13;
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
        } else if(args[0].equals("")) {
            
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
        return "error " + args[0] + (char) 13;
    }
}
