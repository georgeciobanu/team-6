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

/**
 *
 * @author GLL
 */
public class sOrder {
    int m_userID = -1;
    sExchangeRate m_currencyPair;
    double m_amount;
    boolean m_isPending;
    Timestamp expiryDate = null;
    Timestamp timestamp = null;
    int type;
    int basis;
    String currencyPair = null;
    
    /**
     * Creates a new instance of sOrder
     */
    sOrder(int userID) {
        m_userID = userID;
    }
    
    public void setUserID(int id) {
        m_userID = id;
    }
    
    public int getuserID() {
        return m_userID;
    }
    
    public void setCurrencyPair(sExchangeRate er) {
        m_currencyPair = er;
    }
    
    public sExchangeRate getCurrencyPair() {
        return m_currencyPair;
    }
    
    public void setAmount(double amount) {
        m_amount = amount;
    }
    
    public double getAmount() {
        return m_amount;
    }
    
    public void setPending(boolean isPending) {
        m_isPending = isPending;
    }
    
    public boolean isPending() {
        return m_isPending;
    }
    
    // This function won't be implemented
    public boolean cancel() {
        return false;
    }
}
