/*
 * sOrder.java
 *
 * Created on November 30, 2007, 7:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sFundamentals;

import java.sql.Date;
import java.sql.Timestamp;
import fundamentals.*;
import database.*;

/**
 *
 * @author GLL
 */
public class sOrder extends Order{
    sExchangeRate m_currencyPair;
    
    /**
     * Creates a new instance of sOrder
 */ 
    public sOrder(int userID) {
        super (userID);
        setUserID(userID);
    }
    
    public boolean get(int orderID) {
        //Order o = m_db.getOrder(orderID);
        return false;
    }
    
    public void setCurrencyPair(sExchangeRate er) {
        m_currencyPair = er;
    }
    
    // the bla parameter is just a patch
    public sExchangeRate getCurrencyPair(boolean bla) {
        return m_currencyPair;
    }
}
