/*
 * EndUserParser.java
 *
 * Created on November 23, 2007, 12:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snetworking;
import sFundamentals.*;
import database.*;
import transactionEngine.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author GLL
 */
public class EndUserParser {
    DBConnection m_db;
    int m_userID;
    
    /** Creates a new instance of EndUserParser */
    public EndUserParser(DBConnection db, int userID) {
        m_db = db;
        m_userID = userID;
    }
    
    // Parse a string command sent by an end-user
    public String parseCommand(String command) {
        String[] args;
        sEndUser user;
        Market market;
        
        args = command.split(" ");
        
        if(args.length <= 0) {
            return "error" + (char) 13;
        } else if(args[0].equals("getcurrencies") && args.length == 1) {
            String ret = "";
            market = new Market(m_db);
            String[] currenciesList = market.getCurrencies();
            if(currenciesList.length < 1) {
                return "error getcurrencies";
            } else if(currenciesList[0].equals("")) {
                return "error getcurrencies";
            }
            for(int i = 0; i < currenciesList.length; i++) {
                ret += currenciesList[i] + " ";
            }
            return "ok getcurrencies " + ret.trim();
        } else if(args[0].equals("changepassword") && args.length == 2) {
            user = new sEndUser(m_db, m_userID);
            user.setPassword(args[1]);
            return "ok changepassword";
        } else if(args[0].equals("getmarketprice") && args.length == 3) {
            double ret;
            if(args[1].equals("buy")) {
                ret = m_db.getMarketPrice(1, args[2]);
                return "ok getmarketprice " + Double.toString(ret);
            } else if(args[1].equals("sell")) {
                ret = m_db.getMarketPrice(2, args[2]);
                return "ok getmarketprice " + Double.toString(ret);
            }
            
        } else if(args[0].equals("placemarketorder") && args.length == 5) {
            if(args[1].equals("buy")) {
                sMarketOrder o = new sMarketOrder(m_userID);

                sExchangeRate er = new ExchangeRate(args[2]);
                
                o.setCurrencyPair(er);
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                o.setExpiryDate(sdf);
                
                placed, amount, type, expiry, basis, currencyPair 
                
                m_cni.SendMessage("placemarketorder " +
                        "sell "
                        choMarketOrderCurrencyPair.getSelectedItem()
                        txtMarketOrderAmount.getText()
                        choMarketOrderExpiry.getSelectedItem()
                
                
                //java.util.Date date = sdf.parse("2004-07-24 09:45:52.189");
                //java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                
                java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                
                timestamp.
                
                o.setAmount();
                o.setExpiryDate();
                
                m_db.addMarketOrder(o);
            }
            
        } else if(args[0].equals("editmarketorder")) {
            
        } else if(args[0].equals("placelimitorder")) {
            
        } else if(args[0].equals("editlimitorder")) {
            
        } else if(args[0].equals("placestoporder")) {
            
        } else if(args[0].equals("editstoporder")) {
            
        } else if(args[0].equals("placetrailingstoporder")) {
            
        } else if(args[0].equals("edittrailingstoporder")) {
            
        } else if(args[0].equals("pricehistory")) {
            
        } else if(args[0].equals("pendingorder") && args.length == 2) {
            
        } else if(args[0].equals("marketprice")) {
            
        }
        
        // Invalid command
        return "error " + args[0] + (char) 13;
    }
}
