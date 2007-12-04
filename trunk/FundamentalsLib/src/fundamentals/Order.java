/*
 * Order.java
 *
 * Created on November 30, 2007, 7:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author GLL
 */
public class Order {
    int m_userID = -1;
    ExchangeRate m_currencyPair;
    double m_amount;
    boolean m_isPending;
    Timestamp expiryDate = null;
    Timestamp timestamp = null;
    int type;
    int basis;
    
    
    /** Creates a new instance of Order */
    public Order(int userID) {
        m_userID = userID;
    }
    
    public int getType() {
        return type;
    }
    
    public Timestamp getExpiryDate() {
        return expiryDate;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public int getBasis() {
        return basis;
    }        
        
    public void setUserID(int id) {
        m_userID = id;
    }
    
    public int getuserID() {
        return m_userID;
    }
    
    public void setCurrencyPair(ExchangeRate er) {
        m_currencyPair = er;
    }
    
    public ExchangeRate getCurrencyPair() {
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
    
 

    public void setType(int type) {
        this.type = type;
    }
}
