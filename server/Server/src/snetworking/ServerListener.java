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
    DBConnection m_db;
    int m_port = -1;
    
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
            while (true) {
                connection = socket1.accept();
                Runnable runnable = new ClientConnection(m_db, connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
                this.wait(1000);
            }
      } catch (Exception e) {
          System.out.println("Exception: " + e.getMessage());
      } finally {
          try {
              System.out.println("ClientConnection finally");
              if(connection == null) {
                  connection.close();
              }
          } catch (IOException e){}
      }
    }
    
}
