/*
 * MultipleSocketServer.java
 *
 * Created on November 30, 2007, 2:47 PM
 * @author Gabriel Lemonde-Labrecque
 * A part of this code was taken from http://dn.codegear.com/article/31995
 */

package snetworking;

import database.*;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientConnection implements Runnable {

  private Socket connection;
  private String TimeStamp;
  private int ID;
  private DBConnection m_db;

  ClientConnection(DBConnection db, Socket s, int i) {
      this.connection = s;
      this.ID = i;
      this.m_db = db;
  }
  
  public void run() {
      try {
          // Instanciate the client output stream
          BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
          InputStreamReader isr = new InputStreamReader(is);
          
          // Instanciate the network output stream
          BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
          OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
          
          Authentication authUser = new Authentication(m_db);
          
          int character;
          StringBuffer process = new StringBuffer();
          
          // Keep receiving messages until logout or lost connection
          while(true) {
              // Wait for a complete message sent by the client application
              while((character = isr.read()) != 13) {
                  if(character != -1)
                  {
                      process.append((char)character);
                      System.out.println("BufferLength: " + process.length() + " with character: " + character + (char) 13);
                  }
              }
              
              // Log the command (print it out to the console)
              System.out.println(process);
              
              // Preparse command
              String command = process.toString();
              if(command.endsWith("" + (char) 13)) {
                  command = command.substring(0, command.length() - 1);
              }
              
              // Process command
              String returnMessage = authUser.parseCommand(process.toString());
              
              // Clear buffer
              process.delete(0, process.length());
              
              // Output the message back to the client application
              osw.write(returnMessage + (char) 13);
              osw.flush();
          }
          
      } catch (Exception e) {
          System.out.println(e);
      } finally {
          try {
              System.out.println("ClientConnection finally");
              connection.close();
          } catch (IOException e){}
      }
  }
}
