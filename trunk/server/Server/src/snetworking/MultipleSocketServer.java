/*
 * MultipleSocketServer.java
 *
 * Created on November 30, 2007, 2:47 PM
 * @author Gabriel Lemonde-Labrecque
 * A part of this code was taken from http://dn.codegear.com/article/31995
 */

package snetworking;

import java.net.*;
import java.io.*;
import java.util.*;

public class MultipleSocketServer implements Runnable {

  private Socket connection;
  private String TimeStamp;
  private int ID;

  MultipleSocketServer(Socket s, int i) {
      this.connection = s;
      this.ID = i;
  }
  
  public void run() {
      try {
          // Instanciate the client output stream
          BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
          InputStreamReader isr = new InputStreamReader(is);
          
          // Instanciate the network output stream
          BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
          OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
          
          Authentication user = new Authentication();
          
          int character;
          StringBuffer process = new StringBuffer();
          
          // Keep receiving messages until logout or lost connection
          while(true) {
              // Wait for a complete message sent by the client application
              while((character = isr.read()) != 13) {
                  process.append((char)character);
              }
              
              // Log the command (print it out to the console)
              System.out.println(process);
              
              // Process command
              user.parseCommand(process.toString());
              
              // Create a return message
              TimeStamp = new java.util.Date().toString();
              String returnMessage = "MultipleSocketServer repsonded at "+ TimeStamp + (char) 13;
              
              // Output the message back to the client application
              osw.write(returnMessage);
              osw.flush();
          }
          
      } catch (Exception e) {
          System.out.println(e);
      } finally {
          try {
              connection.close();
          } catch (IOException e){}
      }
  }
}

