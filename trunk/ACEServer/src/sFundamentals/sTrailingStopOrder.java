/*
 * sTrailingStopOrder.java
 *
 * Created on November 30, 2007, 8:40 PM
 *
 */

package sFundamentals;

/**
 *
 * @author GLL
 */

public class sTrailingStopOrder extends sOrder {
    double m_trailingPoins;
    
    public sTrailingStopOrder(int userid)
    {
        super (userid);
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

