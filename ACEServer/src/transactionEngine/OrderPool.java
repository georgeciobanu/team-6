/*
 * OrderPool.java
 *
 * Created on November 30, 2007, 9:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package transactionEngine;

import database.*;
import fundamentals.*;
import java.util.Vector;
import java.util.Enumeration;

/**
 *
 * @author Gabriel Lemonde-Labrecque
 */
public class OrderPool {
//    private int numberOfOrders;
//    private int numberOfOrdersPending;
    private DBConnection m_db;
    
    /** Creates a new instance of OrderPool */
    public OrderPool(DBConnection db) {
        m_db = db;
    }
    
    // Returns the order ID
    public int placeOrder(Order newOrder) {
        Order pooledOrder;
        Order.OPERATION operation = newOrder.getOperation();
        
        if(newOrder != null && newOrder.isPending()) {
            try {
                Order.TYPE type = newOrder.getType();
                
                // Get all current pooled orders
                Vector<Order> v = m_db.getAllPendingOrders(newOrder.getCurrencyPair().switchPair(), operation);
                
                double buyPrice = getMarketPrice(Order.OPERATION.BUY, newOrder.getCurrencyPair());
                double sellPrice = getMarketPrice(Order.OPERATION.SELL, newOrder.getCurrencyPair());
                
                if(v != null) {
                    for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
                        pooledOrder = (Order)e.nextElement();
                        if(newOrder.matches(pooledOrder, buyPrice, sellPrice)) {
                            // Commit the transaction to DB
                            makeTransaction(newOrder, pooledOrder, buyPrice, sellPrice);
                            
                            if(newOrder.getAmount() == 0) {
                                return newOrder.getOrderID();
                            }
                            
                            // Check if both users has sufficient funds and disable user accounts is needed
                            updateAccountStatus(pooledOrder.getUserID());
                            updateAccountStatus(newOrder.getUserID());
                        }
                    }
                    newOrder.setStatus(Order.STATUS.PENDING);
                    
                    updateTrailingStops();
                    
                    return m_db.placeOrder(newOrder);
                }
                
                return -1;

            } catch(Exception e) {
                e.printStackTrace();
                return -1;
            }
        } else {
            return -1;
        }
    }
    
    // 
    public boolean makeTransaction(Order newOrder, Order pooledOrder, double buyPrice, double sellPrice) {
        try {
            Order buyingOrder;
            Order soldOrder;
            
            switch(newOrder.getOperation()) {
                case BUY:
                    buyingOrder = newOrder;
                    soldOrder = pooledOrder;
                    break;
                case SELL:
                    buyingOrder = newOrder;
                    soldOrder = pooledOrder;
                    break;
                default:
                    return false;
            }

            if(buyingOrder.getAmount() > soldOrder.getAmount() * buyPrice) {
                // Set 'soldOrder' as matched
                soldOrder.setStatus(Order.STATUS.MATCHED);
                
                // Update the balance account
                m_db.updateBalance(buyingOrder.getUserID(), buyingOrder.getCurrencyPair().getCurrencyFrom(), soldOrder.getAmount() / buyPrice);
                m_db.updateBalance(buyingOrder.getUserID(), buyingOrder.getCurrencyPair().getCurrencyTo(), -soldOrder.getAmount() * buyPrice);
                
                m_db.updateBalance(soldOrder.getUserID(), soldOrder.getCurrencyPair().getCurrencyFrom(), -buyingOrder.getAmount() * sellPrice);
                m_db.updateBalance(soldOrder.getUserID(), soldOrder.getCurrencyPair().getCurrencyTo(), buyingOrder.getAmount() / sellPrice);
                
                // Update the new amount for 'newOrder'
                buyingOrder.setAmount(buyingOrder.getAmount() - soldOrder.getAmount() * buyPrice);
                soldOrder.setAmount(0);
                
            } else if(buyingOrder.getAmount() < soldOrder.getAmount() * buyPrice) {
                // Set 'soldOrder' as matched
                buyingOrder.setStatus(Order.STATUS.MATCHED);
                
                // Update the balance account
                m_db.updateBalance(buyingOrder.getUserID(), buyingOrder.getCurrencyPair().getCurrencyFrom(), soldOrder.getAmount() / buyPrice);
                m_db.updateBalance(buyingOrder.getUserID(), buyingOrder.getCurrencyPair().getCurrencyTo(), -soldOrder.getAmount() * buyPrice);
                
                m_db.updateBalance(soldOrder.getUserID(), soldOrder.getCurrencyPair().getCurrencyFrom(), -buyingOrder.getAmount() * sellPrice);
                m_db.updateBalance(soldOrder.getUserID(), soldOrder.getCurrencyPair().getCurrencyTo(), buyingOrder.getAmount() / sellPrice);
                
                // Update the new amount for 'newOrder'
                soldOrder.setAmount(soldOrder.getAmount() - buyingOrder.getAmount() * sellPrice);
                buyingOrder.setAmount(0);
                
            } else {
                // Set both 'newOrder' and 'pooledOrder' as matched
                newOrder.setStatus(Order.STATUS.MATCHED);
                pooledOrder.setStatus(Order.STATUS.MATCHED);
                
                // Update the balance account
                m_db.updateBalance(buyingOrder.getUserID(), buyingOrder.getCurrencyPair().getCurrencyFrom(), soldOrder.getAmount() / buyPrice);
                m_db.updateBalance(buyingOrder.getUserID(), buyingOrder.getCurrencyPair().getCurrencyTo(), -soldOrder.getAmount() * buyPrice);
                
                m_db.updateBalance(soldOrder.getUserID(), soldOrder.getCurrencyPair().getCurrencyFrom(), -buyingOrder.getAmount() * sellPrice);
                m_db.updateBalance(soldOrder.getUserID(), soldOrder.getCurrencyPair().getCurrencyTo(), buyingOrder.getAmount() / sellPrice);
                
                // Update the new amount for both 'newOrder' and 'pooledOrder'
                buyingOrder.setAmount(0);
                soldOrder.setAmount(0);
            }
            
            // Commit changes to the DB
            m_db.setOrder(newOrder);
            m_db.setOrder(pooledOrder);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check whether a user has sufficient collaterals and disable the account if not. Returns true if the account is enabled and false otherwise
    private boolean updateAccountStatus(int userID) {
        if(m_db.hasSufficientLeverage(userID)) {
            return true;
        } else {
            m_db.disableAccount(userID);
            return false;
        }
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
    
    // Update the price of trailing stop order
    private boolean updateTrailingStops() {
        return false;
    }
}
