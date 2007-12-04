/*
 * EndUser.java
 *
 * Created on November 30, 2007, 8:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;



/**
 *
 * @author GLL
 */
public class EndUser {
    int m_userID;    
    LeverageAccount[] m_leverageAccounts;
    BalanceAccount[] m_balanceAccounts;
    Order[] m_pendingOrders;
    Order[] m_orderHistory;
    
    // Creates a new instance of EndUser
    public EndUser() {
        //to be implemented separately in srv/client
    }
       
   
    
    // Change password of a user
    public boolean setPassword(String newPassword) {
      return false;        
    }
    
    //This function won't be implemented
    public boolean setPriceRange(double p1, double p2) {
        return false;
    }
    
    //This function won't be implemented
    public double[] getPriceRange() {
        return null;
    }
    
    //
    public int createAccount(String username, String password) {
        return 0;
    }
    
    //Place an order
    public boolean placeOrder(Order order) {
        return false;
    }
    
    // This function won't be implemented
    public boolean editOrder(Order order) {
        return false;
    }
    
    // This function won't be implemented
    public boolean cancelOrder(int orderID) {
        return false;
    }
    
    // Get a list of pending orders
    public Order[] getPendingOrders() {
        return null;
    }
    
    // Get all current balances
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
