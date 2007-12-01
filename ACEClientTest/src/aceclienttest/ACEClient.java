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

/* This code was found on http://dn.codegear.com/article/31995 */
/*  The java.net package contains the basics needed for network operations. */
import java.net.*;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;
/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */

public class ACEClient {
    public static void main(String[] args) {
        /** Define a host server */
        String host = "localhost";
        /** Define a port */
        int port = 1234;
        
        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");
        
        try {
            /** Obtain an address object of the server */
            InetAddress address = InetAddress.getByName(host);
            /** Establish a socket connetion */
            Socket connection = new Socket(address, port);
            /** Instantiate a BufferedOutputStream object */
            
            BufferedOutputStream bos = new BufferedOutputStream(connection.
                    getOutputStream());
            
            /** Instantiate an OutputStreamWriter object with the optional character
             * encoding.
             */
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
            
            
            TimeStamp = new java.util.Date().toString();
            String message = "login michael player";
            
            /** Write across the socket connection and flush the buffer */
            osw.write(message + (char)13);
            osw.flush();
            
            
            /** Instantiate a BufferedInputStream object for reading
      /** Instantiate a BufferedInputStream object for reading
             * incoming socket streams.
             */
            
            BufferedInputStream bis = new BufferedInputStream(connection.
                    getInputStream());
            /**Instantiate an InputStreamReader with the optional
             * character encoding.
             */
            
            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
            
            /**Read the socket's InputStream and append to a StringBuffer */
            int c;
            while ( (c = isr.read()) != 13)
                instr.append( (char) c);
            
            /** Close the socket connection. */
            connection.close();
            System.out.println(instr);
        } catch (IOException f) {
            System.out.println("IOException: " + f);
        } catch (Exception g) {
            System.out.println("Exception: " + g);
        }
    }
}
