/*
 * EndUser.java
 *
 * Created on November 30, 2007, 8:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

import database.*;

/**
 *
 * @author GLL
 */
public class EndUser {
    int m_userID;
    DBConnection m_db;
    LeverageAccount[] m_leverageAccounts;
    BalanceAccount[] m_balanceAccounts;
    Order[] m_pendingOrders;
    Order[] m_orderHistory;
    
    /** Creates a new instance of EndUser */
    public EndUser(DBConnection db, int userID) {
        m_db = db;
        m_userID = userID;
    }
    
    public boolean setPassword(String newPassword) {
        return m_db.setUserPassword(m_userID, newPassword);
    }
    
    public boolean setPriceRange(double p1, double p2) {
        return false;
    }
    
    public double[] getPriceRange() {
        return null;
    }
    
    public boolean placeOrder(Order order) {
        return false;
    }
    
    public boolean editOrder(Order order) {
        return false;
    }
    
    // This function won't be implemented
    public boolean cancelOrder(int orderID) {
        return false;
    }
    
    public Order[] getPendingOrders() {
        return null;
    }
    
    public double[] getCurrentBalances() {
        return null;
    }
    
    // This function won't be implemented
    public String getBillingInfo() {
        return "";
    }
    
    public double getCustomizedInterestRate() {
        return 0.0;
    }
    
    public boolean setCustomizedInterestRate(double rate) {
        return false;
    }
    
    public double getCustomizedLeverageRatio() {
        return 0.0;
    }
    
    public boolean setCustomizedLeverageRatio(double ratio) {
        return false;
    }
    
    public double getCustomizedTransactionFee() {
        return 0.0;
    }
    
    public boolean setCustomizedTransactionFee(double fee) {
        return false;
    }

    public double getInterestRate() {
        return 0.0;
    }
    
    public double getTransactionFee() {
        return 0.0;
    }
    
    public double getLeverageRate() {
        return 0.0;
    }

    // This function won't be implemented
    public Order[] getOrderHistory() {
        return null;
    }
    
    public boolean createAccount(String username, String password) {
        return false;
    }
    
    public boolean enableAccount() {
        return false;
    }
    
    public boolean disableAccount() {
        return false;
    }
    
    public boolean deleteAccount() {
        return false;
    }
}
