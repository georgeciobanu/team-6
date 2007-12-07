/*
 * Market.java
 *
 * Created on November 30, 2007, 9:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package transactionEngine;

import java.sql.Timestamp;
import java.sql.Date;
import fundamentals.*;
import database.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author GLL
 */
public class Market {
    private DBConnection m_db;
    private OrderPool pool;
    
    /** Creates a new instance of Market */
    public Market(DBConnection db) {
        m_db = db;
        pool = new OrderPool(m_db);
    }
    
    public int placeOrder(Order order) {
        DateTime dt;
        
        // Commit the order only if the order is pending
        if(order.getStatus() == Order.STATUS.PENDING) {
            // Ensure that the placed date is NOW!
            dt = new DateTime();
            order.setPlacedDate(dt.getTimestamp());
            
            if(order.getType() == Order.TYPE.MARKET) {
                order.setLimit(-1);
                order.setStopLoss(-1);
                order.setTrailingPoints(-1);                
            } else if(order.getType() == Order.TYPE.LIMIT) {
                // Set the limit
                order.setStopLoss(-1);
                order.setTrailingPoints(-1);
            } else if(order.getType() == Order.TYPE.STOPLOSS) {
                
            } else if(order.getType() == Order.TYPE.TRAILINGSTOP) {
                
            }

            // Commit the order to the market
            int orderID = m_db.placeOrder(order);
            return orderID;
        } else {
            return -1;
        }
    }
    
    public boolean editOrder(Order order) {
        return false;
    }
    
    public boolean liquidateAllCurrencies() {
        return false;
    }
    
    public double getMarketPrice(Order.OPERATION operation, CurrencyPair currencypair) {
        double ret = -1;
        
        // Validate currencies
        Currency currencyFrom = currencypair.getCurrencyFrom();
        Currency currencyTo = currencypair.getCurrencyTo();
        
        if(currencyFrom.getID() == -1) {
            currencyFrom.setID(m_db.getCurrencyID(currencyFrom.getName()));
        }
        if(currencyTo.getID() == -1) {
            currencyTo.setID(m_db.getCurrencyID(currencyTo.getName()));
        }
        
        ret = m_db.getMarketPrice(operation, currencypair);
        
        return ret;
    }
    
    public Order[] getPendingOrders() {
        return null;
    }
    
    public double[] getPriceHistory() {
        return null;
    }
    
    public Order[] getOrderHistory() {
        return null;
    }
    
    // Get the list of available traiding currencies on the ACE server
    public String[] getCurrencies() {
        try {
            return m_db.getCurrencies();
        } catch(Exception e) {
            e.printStackTrace();
            String[] empty = {""};
            return empty;
        }
    }
    
    public boolean addCurrency(Currency currency) {
        return false;
    }
    
    public boolean editCurrency(Currency currency) {
        return false;
    }
    
    public boolean removeCurrency(int id) {
        return false;
    }
}
