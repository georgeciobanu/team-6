/*
 * sLimitOrder.java
 *
 * Created on November 30, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sFundamentals;

/**
 *
 * @author GLL
 */

public class sLimitOrder extends sOrder {
    double m_limit;
    double stopLoss;
    
    
    public sLimitOrder(int userid) {
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
