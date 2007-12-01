/*
 * Market.java
 *
 * Created on November 30, 2007, 9:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package transactionEngine;

import fundamentals.*;
import database.*;

/**
 *
 * @author GLL
 */
public class Market {
    
    /** Creates a new instance of Market */
    public Market() {
    }
    
    public boolean placeOrder(Order order) {
        return false;
    }
    
    public boolean editOrder(Order order) {
        return false;
    }
    
    public boolean cancelOrder(int orderID) {
        return false;
    }
    
    public boolean liquidateAllCurrencies() {
        return false;
    }
    
    public double getBuyPrice() {
        return 0.0;
    }
    
    public double getSellPrice() {
        return 0.0;
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
