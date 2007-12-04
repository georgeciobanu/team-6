/*
 * MarketOrder.java
 *
 * Created on November 30, 2007, 8:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

/**
 *
 * @author GLL
 */

public class MarketOrder extends Order {
    // Place a market order
    public boolean place() {
        return false;
    }
    
    public MarketOrder(int userID)
    {
        super(userID);
    }
    
    // Edit a market order
    public boolean edit() {
        return false;
    }
}

