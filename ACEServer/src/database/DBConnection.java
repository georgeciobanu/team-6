/*
 * DBConnection.java
 *
 * Created on November 30, 2007, 5:51 PM
 *
 */

package database;

import java.sql.*;

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
                rs = stm.executeQuery(message);
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
        String cpairs = "";
        
        try{
            String queryString =
                    "SELECT baseCurrency, relativeCurrency " +
                    "FROM currencyPairs ";
            
            ResultSet rs = query(queryString);
            
            while (rs.next() ) { //all went ok
                cpairs = cpairs +  rs.getString("baseCurrency") + "/" + rs.getString("relativeCurrency") + "\n";
            }
            rs.close();
                        
            return cpairs.split("\n");
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
            
            while (rs.next() ) { //all went ok
                c = c +  rs.getString("symbol") + "\n";
            }
            rs.close();
            
            return c.split("\n");
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
                
                rs.close();
                return id;
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        }
        
        return -1;
    }
    
    //
    
    
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
