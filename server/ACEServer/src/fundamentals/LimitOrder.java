/*
 * LimitOrder.java
 *
 * Created on November 30, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

/**
 *
 * @author GLL
 */

public class LimitOrder extends Order {
    double m_limit;
    double stopLoss;
    
    // Place a market order
    public boolean place() {
        return false;
    }
    
    // Edit a market order
    public boolean edit() {
        return false;
    }
}
