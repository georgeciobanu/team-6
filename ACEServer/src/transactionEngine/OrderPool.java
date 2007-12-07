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
    
    public boolean matchOrder(Order order) {
        if(order != null && order.isPending()) {
            try {
                
                Order.TYPE type = order.getType();
                
                switch(type) {
                    case MARKET:
                        return matchMarketOrder(order);
                    case LIMIT:
                        return matchLimitOrder(order);
                    case STOPLOSS:
                        return matchStopLossOrder(order);
                    case TRAILINGSTOP:
                        return matchTrailingStopOrder(order);
                    default:
                        return false;
                }
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    
    
    private boolean matchMarketOrder(Order newOrder) {
        Order pooledOrder;
        Order.OPERATION operation;
        
        try {
            // Invert the operation so that we can look for matching pending orders
            switch(newOrder.getOperation()) {
                case BUY:
                    operation = Order.OPERATION.SELL;
                    break;
                case SELL:
                    operation = Order.OPERATION.BUY;
                default:
                    return false;
            }
            
            // Get all current pooled orders
            Vector<Order> v = m_db.getAllPendingOrders(newOrder.getCurrencyPair(),operation);
            
            // Try to match the 'newOrder' with the currently pooled orders
            if(v != null) {
                for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
                    pooledOrder = (Order)e.nextElement();
                    if(newOrder.getAmount() > pooledOrder.getAmount()) {
                        // Set 'pooledOrder' as matched
                        pooledOrder.setStatus(Order.STATUS.MATCHED);
                        
                        // Update the new amount for 'newOrder'
                        newOrder.setAmount(newOrder.getAmount() - pooledOrder.getAmount());
                        pooledOrder.setAmount(0);
                        
                        // Commit changes to the DB
                        m_db.updateOrder(newOrder);
                        m_db.updateOrder(pooledOrder);
                        
                        // Check if both users has sufficient funds and disable user accounts is needed
                        
                    } else if(newOrder.getAmount() < pooledOrder.getAmount()) {
                        // Set 'newOrder' as matched
                        newOrder.setStatus(Order.STATUS.MATCHED);
                        
                        // Update the new amount for 'pooledOrder'
                        pooledOrder.setAmount(pooledOrder.getAmount() - newOrder.getAmount());
                        newOrder.setAmount(0);
                        // Commit changes to the DB
                        m_db.updateOrder(newOrder);
                        m_db.updateOrder(pooledOrder);
                        
                        // Check if both users has sufficient funds and disable user accounts is needed
                        
                    } else {
                        // Set both 'newOrder' and 'pooledOrder' as matched
                        newOrder.setStatus(Order.STATUS.MATCHED);
                        pooledOrder.setStatus(Order.STATUS.MATCHED);
                        
                        // Update the new amount for both 'newOrder' and 'pooledOrder'
                        newOrder.setAmount(0);
                        pooledOrder.setAmount(0);
                        
                        // Do the transaction
                        doTransaction(newOrder, pooledOrder);
                        
                        // Check if both users has sufficient funds and disable user accounts is needed
                        updateAccountStatus(pooledOrder.getUserID());
                        if(!updateAccountStatus(newOrder.getUserID())) {
                            return true;
                        }
                    }
                }
                
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 
    public boolean doTransaction(Order newOrder, Order pooledOrder) {
        try {
            // Commit changes to the DB
            m_db.updateOrder(newOrder);
            m_db.updateOrder(pooledOrder);
            
            // Update the balance accounts of both users
            if(newOrder.getOperation() == Order.OPERATION.BUY) {
                m_db.updateBalance(newOrder.getUserID(), newOrder.getCurrencyPair().getCurrencyFrom(), newOrder.getAmount());
                m_db.updateBalance(newOrder.getUserID(), newOrder.getCurrencyPair().getCurrencyTo(), -newOrder.getAmount());
                
                m_db.updateBalance(pooledOrder.getUserID(), pooledOrder.getCurrencyPair().getCurrencyFrom(), -pooledOrder.getAmount());
                m_db.updateBalance(pooledOrder.getUserID(), pooledOrder.getCurrencyPair().getCurrencyTo(), pooledOrder.getAmount());
            } else if(newOrder.getOperation() == Order.OPERATION.SELL) {
                m_db.updateBalance(newOrder.getUserID(), newOrder.getCurrencyPair().getCurrencyFrom(), -newOrder.getAmount());
                m_db.updateBalance(newOrder.getUserID(), newOrder.getCurrencyPair().getCurrencyTo(), newOrder.getAmount());
                
                m_db.updateBalance(pooledOrder.getUserID(), pooledOrder.getCurrencyPair().getCurrencyFrom(), pooledOrder.getAmount());
                m_db.updateBalance(pooledOrder.getUserID(), pooledOrder.getCurrencyPair().getCurrencyTo(), -pooledOrder.getAmount());
            } else {
                /// HUGE BUG IF GET HERE! It should not happen though.
                Exception f = new Exception("Orders were committed but operation was undefined.");
                throw f;
            }
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
    
    private boolean matchLimitOrder(Order order) {
        return false;
    }
    
    private boolean matchStopLossOrder(Order order) {
        return false;
    }
    
    private boolean matchTrailingStopOrder(Order order) {
        return false;
    }
    
    // Update the price of trailing stop order
    private boolean updateTrailingStops() {
        return false;
    }
}
