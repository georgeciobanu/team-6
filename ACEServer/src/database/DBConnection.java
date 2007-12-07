/*
 * DBConnection.java
 *
 * Created on November 30, 2007, 5:51 PM
 *
 */

package database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import fundamentals.*;
import fundamentals.Currency;


/**
 *
 * @author George
 */
public class DBConnection {
    private boolean connected;
    private Connection con;
    
    public static enum USERSTATUS {
        NOTAUTHENTICATED,
        ADMINISTRATOR,
        ENDUSER
    }
    
    
    
    // TODO: THESE FUNCTIONS ARE REQUIRED BY THE REST OF THE SYSTEM
    
    // Get all pending orders that matches the currency pair and the operation
    // Input: A currency pair and an operation (sell/buy)
    // Output: a vector of all pending orders that match the currency pair and the operation (THE RETURNED LIST SHOULD BE LISTED IN DESCENDING ORDER OF PLACED DATE)
    public Vector<Order> getAllPendingOrders(CurrencyPair currencypair, Order.OPERATION operation) {
        return null;
    }
    
    // Fill a currency object with both name and id, given that at least one of these attributes are set
    // Input: The currency object to be filled
    // Output: The filled currency object OR 'null' if none of the attributes are set.
    public Currency FillCurrency(Currency currency) {
        return null;
    }
    
    // Update an order's content to the database
    // Input: An order that has its ID set to the proper value in the DB
    // Output: Returns true if the order has been updated and false otherwise
    public boolean updateOrder(Order order) {
        return true;
    }
    
    // Check if the user has sufficient leverage (collaterals)
    // Input: The user ID
    // Output: true if has sufficient collaterals and false otherwise
    public boolean hasSufficientLeverage(int userID) {
        return true;
    }
    
    // Disable the user account
    // Input: The user ID
    // Output: true if the operation was committed to the DB and false otherwise
    public boolean disableAccount(int userID) {
        return true;
    }
    
    // Update the balance account of a user
    // Input: The user ID, the currency of the account to be modified, the positive or negative difference to apply to the account
    // Output: 
    public boolean updateBalance(int userID, Currency currency, double deltaAmount) {
        return true;
    }
    
    // Check whether a user has his account disabled
    public boolean isAccountEnabled(int userID) {
        return false;
    }
    
    public DBConnection() { //this needs to be called on startup        
        connected = false;
    }

    public boolean connect(String ODBCname, int port) {
        if (! connected) {
            try {
                //we need this to be a class member
                con = DriverManager.getConnection("jdbc:odbc:acedb", "admin","admin");
                connected = true;
                
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else return true;
        
        return false;
        
    }
    
    private ResultSet query(String message){
        ResultSet rs = null;
        try {
            if (connected) {
                
                Statement stm = con.createStatement();
                if (! message.startsWith("INSERT") && ! message.startsWith("UPDATE"))
                    rs = stm.executeQuery(message);
                else stm.executeUpdate(message);
                
                //stm.close();
            }
        } catch (Exception ex){
            System.out.println(message);
            ex.printStackTrace();
        }
        return rs;
    }
    
    public boolean setUserPassword(int userid, String newPassword) {
        if (newPassword.length() > 1) {
            try{
                String queryString =
                        "UPDATE users " +
                        "SET password='" + newPassword + "' " +
                        "WHERE userid=" + String.valueOf(userid);
                
                ResultSet rs = query(queryString);
                
                //check if the value was saved
                queryString =
                        "SELECT password " +
                        "FROM users " +
                        "WHERE userid=" + String.valueOf(userid);
                rs = query(queryString);
                String setPassword;
                if (rs.next() && (newPassword.equals(rs.getString("password"))) ) { //all went ok
                    rs.close();
                    return true;
                }
                rs.close();
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    public String[] getCurrencies() {
        String c = "";
        
        try{
            String queryString =
                    "SELECT name " +
                    "FROM currencies";
            
            ResultSet rs = query(queryString);
            
            Vector<String> list = new Vector();
            
            while (rs.next() ) { //all went o
                list.add(rs.getString("name"));
            }
            rs.close();
            
            String s [] = list.toArray(new String[0]);
            return s;
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
            return null;
        }
    }
    
    // Takes a currency name and returns its ID
    public int getCurrencyID(String name) {
        int id = -1;
        try{
            if (name.length() > 0) {
                String queryString =
                        "SELECT id " +
                        "FROM currencies " +
                        "WHERE name='" + name + "'";
                
                ResultSet rs = query(queryString);
                if (rs.next()) {
                    id = rs.getInt("id");
                    rs.close();
                }
                //rs.close();
            }
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
        } finally {
            return id;
        }
    }
    
    // Takes a currency name and returns its ID
    public boolean depositFunds(int userID, Currency currency, double amount) {
        int id = -1;
        double leverage;
        ResultSet rs = null;
        
        if(amount <= 0) return false;
        
        try{
            String queryString =
                    "SELECT id, leverage " +
                    "FROM leverageaccounts " +
                    "WHERE userid=" + userID + " " +
                    "AND currencyid=" + currency.getID() + " ";
            
            try {
                rs = query(queryString);
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                
                if(id >= 0) {
                    leverage = rs.getDouble("leverage");
                    leverage += amount;
                    
                    // Save deposit to DB
                    queryString =
                            "UPDATE leverageaccounts " +
                            "SET leverage=" + String.valueOf(leverage) + " " +
                            "WHERE userid=" + userID + " " +
                            "AND currencyid=" + String.valueOf(currency.getID());
                    
                    // Try to update the new leverage amount
                    try {
                        rs = query(queryString);
                        return true;
                    } catch(Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    rs.close();
                    queryString =
                            "INSERT INTO leverageaccounts (userid, currencyid, leverage) " +
                            "VALUES (" + userID + "," + currency.getID() + "," + amount + ")";
                    try {
                        rs = query(queryString);
                        rs.close();
                        return true;
                    } catch(Exception f) {
                        f.printStackTrace();
                        return false;
                    }
                }
            } // If there not yet an account for this currency, we add it
            catch(Exception e) {
                queryString =
                        "INSERT INTO leverageaccounts (userid, currencyid, leverage) " +
                        "VALUES (" + userID + "," + currency.getID() + "," + amount + ")";
                try {
                    rs = query(queryString);
                    return true;
                } catch(Exception f) {
                    f.printStackTrace();
                    return false;
                }
            }

        } catch (Exception g){ //TODO: treat exceptions nice
            g.printStackTrace();
            return false;
        }
    }
    
    // Get the list of all users
    // Input: NONE
    // Output: the list of all users (separated with space)
    public Vector<String> getUsernames(USERSTATUS ut) {
      try {
            Vector<String> list = new Vector<String>(100);
            SQLFormatter sql = new SQLFormatter();
          
            String s = "";

            if(ut == USERSTATUS.ENDUSER) {
                s = "0";
            } else if(ut == USERSTATUS.ADMINISTRATOR) {
                s = "1";
            } else {
                return null;
            }
                
            String queryString =
                    "SELECT username FROM users WHERE type=" + s;
            
            ResultSet rs = query(queryString);
            
            // TODO: Make this more than 100
            int i = 0;
            while(rs.next() && i < 99) {
                list.add(rs.getString("username"));
                i++;
            }
            return list;
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public int getUserID(String username, String password) {
        if (username.length() > 0 && password.length() > 0) {
            try{
                String queryString =
                        "SELECT userid " +
                        "FROM users " +
                        "WHERE username='" + username + "' AND password='" + password+ "' ";
                
                ResultSet rs = query(queryString);
                if (rs.next()) {
                    int userid = rs.getInt("userid");
                    rs.close();
                    return userid;
                }
                rs.close();
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        
        return -1;
    }
    
    // This function should only be used by the administrators
    public int getUserID(String username) {
        if (username.length() > 0) {
            try{
                String queryString =
                        "SELECT userid " +
                        "FROM users " +
                        "WHERE username='" + username + "'";
                
                ResultSet rs = query(queryString);
                if (rs.next()) {
                    int userid = rs.getInt("userid");
                    rs.close();
                    return userid;
                }
                rs.close();
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        
        return -1;
    }
    
    // Create a new end-user account
    // Input: username, password
    // Output: user ID, or -1 if error
    public int createAccount(String username, String password) {
        if (username.length() > 0 && password.length() > 0) {
            try{
                // Check if the username already exists
                if(getUserID(username) == -1) {
                    String queryString =
                            "INSERT INTO users (username, password, type) " +
                            " VALUES (" + "'" + username+ "'" + ", " + "'" + password + "',1)" ;
                    
                    ResultSet rs = query(queryString);
                    
                    int id = getUserID(username, password);
                    
                    //rs.close();
                    return id;
                } else {
                    return -1;
                }
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        return -1;
    }
    
    public int createAccount(String username, String password, int type, String contactInfo,
            String email, double transactionFee, double interestRate, double leverageRatio) {
        if (username.length() > 0 && password.length() > 0) {
            try{
                String queryString =
                        "INSERT INTO users (username, password, type, " +
                        "contactInfo, email, transactionFee, interestRate, leverageRatio) " +
                        " VALUES ('" + username + "', '" + password + "', " + String.valueOf(type) + ", '" + contactInfo + "', '" +
                        email + "', " +
                        String.valueOf(transactionFee) + ", " +
                        String.valueOf(interestRate) + ", " +
                        String.valueOf(leverageRatio) + ")" ;
                
                ResultSet rs = query(queryString);
                
                int id = getUserID(username, password);
                
                return id;
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        
        return -1;
    }
    
    // Get an order's information given its ID
    // Input: the user ID and the order ID
    // Output: an order object containing all order data
    public Order getOrder(int userID, int orderID) {
      try {
            String queryString =
                    "SELECT status, placed, amount, type, operation, duration, price, currencyfromid, currencytoid, limit, loss, trailingpoints " + 
                    "FROM orderpool " +
                    "WHERE userid=" + userID + " " +
                    "AND id=" + orderID + " "; // + "ORDER BY placed DESC";
            
            ResultSet rs = query(queryString);
            
            Order order = new Order(userID);
            
            if(rs.next()) {
                order.setStatus(rs.getInt("status"));
                order.setPlacedDate(rs.getTimestamp("placed"));
                order.setAmount(rs.getDouble("amount"));
                order.setType(rs.getInt("type"));
                order.setOperation(rs.getInt("operation"));
                order.setDuration(rs.getInt("duration"));
                //order.setExpiry(new Timestamp(rs.getDate("expiry").getTime()));
                order.setPrice(rs.getDouble("price"));
                order.setCurrencyPair(new CurrencyPair(rs.getString("currencyfromid"), rs.getString("currencytoid")));
                order.setLimit(rs.getDouble("limit"));
                order.setStopLoss(rs.getDouble("loss"));
                order.setTrailingPoints(rs.getDouble("trailingpoints"));
                return order;
            }
            return null;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // Get the list of a user's orders ID
    // Input: the user ID
    // Output: the list of his orders' ID
    public Vector<Integer> getPendingOrderIDList(int userID) {
      try {
            Vector<Integer> list = new Vector<Integer>(100);
            SQLFormatter sql = new SQLFormatter();
          
            String queryString =
                    "SELECT id FROM orderpool " +
                    "WHERE userid=" + userID + " " +
                    "AND status=" + sql.getString(Order.STATUS.PENDING) + " "; // +
                    //"ORDER BY placed DESC";
            
            ResultSet rs = query(queryString);
            
            // Make this more than 100
            int i = 0;
            while(rs.next() && i < 99) {
                list.add(new Integer(rs.getInt(1)));
                i++;
            }
            return list;
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public int placeOrder(Order order) {
        try{
            DateTime placed = new DateTime(order.getPlacedDate());
            
            // Commit the order to the database
            SQLFormatter sql = new SQLFormatter();
            ResultSet rs = query(sql.InsertOrder(order));
            
            // Get the ID of the order we just placed
            String queryString =
                    "SELECT id " +
                    "FROM orderpool " +
                    "WHERE userid=" + order.getUserID() + " " +
                    "AND placed=#" + placed.getSQL() + "#";
            rs = query(queryString);
            if (rs.next()) {
                int orderID = rs.getInt("id");
                rs.close();
                return orderID; //generated by the db
            }
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
            return -1;
        }
        return -1;
    }    
    
    // Get market price
    // The buy price is set to the highest limit someone is willing to pay (set by limit and trailing stop orders)
    // The sell price is the lowest stoploss someone is willing to pay (only set by limit orders)
    public double getMarketPrice(Order.OPERATION operation, CurrencyPair currencyPair) {
        try{
            SQLFormatter sql = new SQLFormatter();
            
            String queryString = //note: check what the timestamp is
                    "SELECT price " +
                    "FROM orderpool " +
                    "WHERE status=" + sql.getString(Order.STATUS.PENDING) + " " +
                    "AND operation=" + sql.getString(operation) + " " +
                    "AND currencyfromid=" + currencyPair.getCurrencyFrom().getID() + " " +
                    "AND currencytoid=" + currencyPair.getCurrencyTo().getID(); //+
                    //"SORTED BY placed DESC";
            
            ResultSet rs = query(queryString);
            
            if (rs.next()) {
                return rs.getDouble("price");
            }
            
        } catch (Exception ex) { //TODO: treat exceptions nice
            ex.printStackTrace();
            return -1;
        }
        
        return -1;
    }
    

    public USERSTATUS getUserType(int userID) {
        if (userID > 0) {
            try{
                String queryString =
                        "SELECT type " +
                        "FROM users " +
                        "WHERE userid=" + String.valueOf(userID);
                
                ResultSet rs = query(queryString);
                
                if (rs.next()) {
                    int userType = rs.getInt("type");
                    rs.close();
                    switch (userType) {
                        case 1:
                        {
                            return USERSTATUS.ENDUSER;
                        }
                        case 0:
                        {
                            return USERSTATUS.ADMINISTRATOR;
                        }
                        default:
                        {
                            return USERSTATUS.NOTAUTHENTICATED;
                        }
                        
                    }
                }
                rs.close();
            } catch (Exception ex){//TODO: treat exceptions nice
                ex.printStackTrace();
                return USERSTATUS.NOTAUTHENTICATED;
            }
        }
        
        return USERSTATUS.NOTAUTHENTICATED;
    }
    
    public void disconnect() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
