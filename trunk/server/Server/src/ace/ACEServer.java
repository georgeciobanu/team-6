package ace;
/*
 * ACEServer.java
 *
 * Created on November 22, 2007, 8:16 PM
 *
 */

/**
 *
 * @author Alex Ciobanu
 */
public class ACEServer {
    
    // constructor
    public ACEServer() {
    }
        
    // OS entry point
    public static void main(String[] args) {
        
        ACEServer s = new ACEServer();
        
        if (s.startup()) {
            System.out.println("All systems ok!");
        }
        
        // TODO
        //    - here wait for any threads to finish...
        
        s.shutdown();
        
    }
    
    /**
     * starts the server
     *
     * Returns true when successful and false otherwise
     */
    public boolean startup() {

        // TODO:
        //    - in a separate thread run the part that 
        //      listens to the sockets
        
        System.out.println("ACEServer is up!");
        
        return true;
    }
    

    /**
     * shuts the server down
     *
     * Returns true when successful and false otherwise
     */
    public void shutdown() {

        System.out.println("ACEServer is now down!");
                
    }    
    
}
