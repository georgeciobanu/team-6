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
    private OrderPool m_pool;
    
    /** Creates a new instance of Market */
    public Market(DBConnection db) {
        m_db = db;
        m_pool = new OrderPool(m_db);
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
                order.setTrailingPoints(-1);
            } else if(order.getType() == Order.TYPE.STOPLOSS) {
                order.setTrailingPoints(-1);
            } else if(order.getType() == Order.TYPE.TRAILINGSTOP) {
                // Set the limit
                order.setLimit(getMarketPrice(order.getOperation(),order.getCurrencyPair()) - order.getTrailingPoints());
                order.setStopLoss(-1);
            }

            // Commit the order to the market
            int orderID = m_pool.placeOrder(order);
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
        return m_pool.getMarketPrice(operation, currencypair);
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
