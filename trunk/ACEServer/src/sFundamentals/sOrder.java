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

/**
 *
 * @author GLL
 */
public class sOrder extends Order{
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
        //super (userID);
        m_userID = userID;
    }        
 
}
