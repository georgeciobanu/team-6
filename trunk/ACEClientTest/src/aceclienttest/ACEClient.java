package aceclienttest;

/*
 * ACEClient.java
 *
 * Created on December 1, 2007, 12:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author GLL
 */

public class ACEClient {
    
    public ACEClient() {
        /** Define a host server */
        String host = "localhost";
        /** Define a port */
        int port = 1234;
        String message;
        
        ClientNetworkInterface cni = new ClientNetworkInterface();
        if(cni.connect("localhost",1234))
        {
            cni.SendMessage("login Michael who");
            //while(!cni.newReceivedMessage());
            //message = cni.ReceiveMessage();
            
            //cni.SendMessage("getcurrencies");
            while((message = cni.ReceiveMessage()).equals("") && cni.isConnected());
            
            
            
            cni.disconnect();
        }
    }
    
    public static void main(String[] args) {
        new ACEClient();
    }
}
