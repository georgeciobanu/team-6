/*
 * ServerListener.java
 *
 * Created on December 1, 2007, 2:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snetworking;

import database.*;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author GLL
 */
public class ServerListener implements Runnable {
    private DBConnection m_db;
    private int m_port = -1;
    private boolean m_stop = false;
    private Thread [] clients;
    
    
    
    /** Creates a new instance of ServerListener */
    public ServerListener(DBConnection db, int port) {
        m_db = db;
        m_port = port;
    }
    
    public void run() {
      Socket connection = null;
      
      try {
          int count = 0;
          ServerSocket socket1 = new ServerSocket(m_port);
          
          System.out.println("Server started listening...");
          while(!m_stop) {
              connection = socket1.accept();
              Runnable runnable = new ClientConnection(m_db, connection, ++count);              
              Thread thread = new Thread(runnable);
              //clients[count] = thread;
              thread.start();
              
              //need to wait 10 seconds to pretend that we're processing something
              try {
                  Thread.sleep(500);
              } catch (Exception e){}
          }
      } catch (Exception e) {
          System.out.println("Exception: " + e.getMessage());
      } finally {
          try {
              System.out.println("ClientConnection finally");
              if(connection != null) {
                  connection.close();
              }
          } catch (IOException e){}
      }
    }
    public void stopMe() {
        m_stop = true;
    }
}
