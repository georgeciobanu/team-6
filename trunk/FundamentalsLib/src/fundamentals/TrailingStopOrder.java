/*
 * TrailingStopOrder.java
 *
 * Created on November 30, 2007, 8:40 PM
 *
 */

package fundamentals;

/**
 *
 * @author GLL
 */

public class TrailingStopOrder extends Order {
    
    public TrailingStopOrder(int userid) {
        super(userid);
    }

    // Place a market order
    public boolean place() {
        return false;
    }
    
    // Edit a market order
    public boolean edit() {
        return false;
    }
}

