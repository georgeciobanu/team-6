/*
 * DBConnection.java
 *
 * Created on November 30, 2007, 5:51 PM
 *
 */

package database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
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
    
    public static enum BUYSELL {
        INVALID,
        BUY,
        SELL
    }
    
    public DBConnection() { //this needs to be called on startup
        
        connected = false;
    }
    
    
    
    public boolean connect(String ODBCname, int port) {
        if (! connected) {
            try {
                //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                
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
        try{
            if (connected) {
                
                Statement stm = con.createStatement();
                if (! message.startsWith("INSERT") && ! message.startsWith("UPDATE"))
                    rs = stm.executeQuery(message);
                else stm.executeUpdate(message);
                
                //stm.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return rs;
    }
    
    public boolean setUserPassword(int userid, String newPassword) {
        if (newPassword.length() > 1) {
            try{
                String queryString =
                        "UPDATE users " +
                        "SET password = '" + newPassword + "' " +
                        "WHERE userid=" + String.valueOf(userid);
                
                ResultSet rs = query(queryString);
                
                //check if the value was saved
                queryString =
                        "SELECT password " +
                        "FROM users " +
                        "WHERE userid =" + String.valueOf(userid);
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
    
    public String[] getCurrencyPairs() {
        try{
            String queryString =
                    "SELECT baseCurrency, relativeCurrency " +
                    "FROM currencyPairs ";
            
            ResultSet rs = query(queryString);
            
            Vector<String> list = new Vector();
            
            while (rs.next() ) { //all went o
                list.add(rs.getString("baseCurrency") + "/" + rs.getString("relativeCurrency") );
            }
            rs.close();
            
            String s [] = list.toArray(new String[0]);
            
            return s;
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
            return null;
        }
    }
    
    public String[] getCurrencies() {
        String c = "";
        
        try{
            String queryString =
                    "SELECT symbol " +
                    "FROM currencies ";
            
            ResultSet rs = query(queryString);
            
            Vector<String> list = new Vector();
            
            while (rs.next() ) { //all went o
                list.add(rs.getString("symbol"));
            }
            rs.close();
            
            String s [] = list.toArray(new String[0]);
            return s;
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
            return null;
        }
    }
    
    public int getUserID(String username, String password) {
        if (username.length() > 0 && password.length() > 0) {
            try{
                String queryString =
                        "SELECT userid " +
                        "FROM users " +
                        "WHERE username ='" + username + "' AND password = '" + password+ "' ";
                
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
                        "INSERT  INTO users (username, password) " +
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
                        "INSERT  INTO users (username, password, type, " +
                        "contactInfo, email, transactionFee, interestRate, leverageRatio) " +
                        " VALUES (" + "'" + username+ "'" + ", " + "'" + password + "' ," + String.valueOf(type) + " , '" + contactInfo + "' ," +
                        "'" + email + "'" + ", " +
                        String.valueOf(transactionFee)  + "," +
                        String.valueOf(interestRate) + "," +
                        String.valueOf(leverageRatio) +
                        ")" ;
                
                ResultSet rs = query(queryString);
                
                int id = getUserID(username, password);
                
                // rs.close();
                return id;
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        
        return -1;
    }
    
    
    public int addMarketOrder(sMarketOrder order) {
        //if (username.length() > 0 && password.length() > 0) {
        try{
            String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
            
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            String now =  sdf.format(cal.getTime());
            
            
            String queryString = //note: check what the timestamp is
                    "INSERT  INTO marketOrders (userID, placed, amount, type, expiry, basis, currencyPair " + ")" +
                    "VALUES (" + String.valueOf(order.getuserID()) + ", #" + now + "#, " +
                    String.valueOf(order.getAmount()) + ", " + String.valueOf(order.getType()) + ", #" +
                    order.getExpiryDate().toString().replaceFirst("\\.[0-9]{2,9}", "") + "#, " + String.valueOf(order.getBasis()) + ", " +
                    "'USD/CAD'" + //TODO: to change to actual value
                    ")" ;
            
            ResultSet rs = query(queryString);
            
            queryString =
                    "SELECT id " +
                    "FROM marketOrders " +
                    "WHERE userid =" + order.getuserID() + " AND placed=#" +
                    now + "# ";
            
            rs = query(queryString);
            if (rs.next()) {
                int marketOrderid = rs.getInt("id");
                rs.close();
                return marketOrderid; //generated by the db
            }
        } catch (Exception ex){ //TODO: treat exceptions nice
            ex.printStackTrace();
            return -1;
        }
        return -1;
    }
    
    
    // Get market price
    
    public double getMarketPrice(int buysell, String currencyPair) {
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
    }
    
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
            }
            sMarketOrder[] tmp = new sMarketOrder[1];
            return orderV.toArray( tmp );
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public USERSTATUS getUserType(int userID) {
        if (userID > 0) {
            try{
                String queryString =
                        "SELECT type " +
                        "FROM users " +
                        "WHERE userid =" + String.valueOf(userID) + " ";
                
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