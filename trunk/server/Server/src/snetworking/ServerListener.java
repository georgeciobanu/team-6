/*
 * ServerListener.java
 *
 * Created on December 1, 2007, 2:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snetworking;

/**
 *
 * @author GLL
 */
public class ServerListener implements Runnable {
    
    /** Creates a new instance of ServerListener */
    public ServerListener() {
    }
    
    public void run() {
      try {
        try{
            int count = 0;
            m_port = port;
            ServerSocket socket1 = new ServerSocket(port);
            m_isAccepting = true;
            System.out.println("Server started listening...");
            while (true) {
                Socket connection = socket1.accept();
                Runnable runnable = new ClientConnection(m_db, connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
            //return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
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
