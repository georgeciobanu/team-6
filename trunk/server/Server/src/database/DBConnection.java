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
    
    
    
    public boolean connect(String server, int port) {
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
    
    public USERSTATUS getUserID(String username, String password) {
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
                    switch (userid)
                    {
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
            } catch (Exception ex){ //TODO: treat exceptions nice
                ex.printStackTrace();
                return USERSTATUS.NOTAUTHENTICATED;
            }
        } 
        
        return USERSTATUS.NOTAUTHENTICATED;
    }
    
    
    public int getUserType(int userID) {
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
                    return userType;
                }
                rs.close();
            } catch (Exception ex){//TODO: treat exceptions nice
                ex.printStackTrace();
                return -1;
            }
        } else return -1;
        
        return -1;
    }
    
    public void disconnect() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
