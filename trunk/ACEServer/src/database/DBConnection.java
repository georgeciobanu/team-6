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
import sFundamentals.sMarketOrder;
import sFundamentals.sOrder;


/**
 *
 * @author GLL
 */
public class DBConnection {
    private boolean connected;
    private Connection con;
    
    public static enum USERSTATUS {
        NOTAUTHENTICATED,
        ADMINISTRATOR,
        ENDUSER
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
                String queryString =
                        "INSERT INTO users (username, password) " +
                        " VALUES (" + "'" + username+ "'" + ", " + "'" + password + "')" ;
                
                ResultSet rs = query(queryString);
                
                int id = getUserID(username, password);
                
                //rs.close();
                return id;
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        return -1;
    }
    
    public int createAccount(String username, String password, int type, String contactInfo,
            String email, float transactionFee, float interestRate, float leverageRatio) {
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
            System.out.println("1");
            String queryString =
                    "SELECT status, placed, amount, type, operation, duration, price, currencyfromid, currencytoid, limit, loss, trailingpoints " + 
                    "FROM orderpool " +
                    "WHERE userid=" + userID + " " +
                    "AND id=" + orderID + " "; // + "ORDER BY placed DESC";
            
            ResultSet rs = query(queryString);

            System.out.println("2");
            
            Order order = new Order(userID);
            
            System.out.println("3");
            // Fetch a maximum number of 10 pending orders from the database
            
            if(rs.next()) {
                System.out.println("3.5");
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
                System.out.println("4");
                return order;
            }
            System.out.println("5");
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
                    //"AND placed='" + sql.Time(order.getPlacedDate()) + "'";
                    //"AND placed='01/01/2007 2:30:00 AM'";
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
    
    /*public double getMarketPrice(int buysell, String currencyPair) {
        try{
            String queryString = //note: check what the timestamp is
                    "SELECT max(price) " +
                    "FROM orderpool " +
                    "WHERE orderstatus=1 AND buysell =" + String.valueOf(buysell) + " AND  currencypair ='" + currencyPair + "' "; //orderstatus=1 ==> pending order
            
            
            ResultSet rs = query(queryString);
            
            //int id = getMarketOrder last
            if (rs.next()) {
                double price = rs.getDouble(1);
                return price;                
            }
            
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
            return -1;
        }
        
        return -1;
    }*/
    
    /*
    public sMarketOrder[] getPendingOrders(int userID) {
        try {
                        
            String queryString =
                    "SELECT placed, amount, type, expiry, basis, currencyPair " +
                    "FROM marketOrders " +
                    "WHERE userID=" + userID + " " +
                    "ORDER BY placed";
            
            ResultSet rs = query(queryString);
            
            int i = 0;
            Vector<sMarketOrder> orderV = new Vector();
            sMarketOrder order = new sMarketOrder(userID);
            
            // Fetch a maximum number of 10 pending orders from the database
            while( rs.next() && i < 10) {
                order.setAmount(rs.getDouble("amount"));
                order.setExpiryDate( new Timestamp (rs.getDate("expiry").getTime()));
                order.setCurrencyPairS(rs.getString("currencypair"));                
                order.setType(rs.getInt("type"));                
                
                orderV.add(order);
                i++;
            }
            sMarketOrder[] tmp = new sMarketOrder[1];
            return orderV.toArray( tmp );
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    */
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
