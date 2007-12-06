/*
 * sMarketOrder.java
 *
 * Created on November 30, 2007, 8:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sFundamentals;
import database.*;

/**
 *
 * @author GLL
 */

public class sMarketOrder extends sOrder {
    
    public sMarketOrder(int userid) {
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

