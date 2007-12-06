/*
 * EndUserParser.java
 *
 * Created on November 23, 2007, 12:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snetworking;
import fundamentals.*;
import sFundamentals.*;
import database.*;
import transactionEngine.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import fundamentals.Currency;

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
        
        // If server receives an empty command
        if(args.length <= 0) {
            return "error" + (char) 13;
            
        } // If server receives the 'getcurrencies' command
        else if(args[0].equals("getcurrencies") && args.length == 1) {
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
            
        } // If server receives the 'changepassword' command
        else if(args[0].equals("changepassword") && args.length == 2) {
            user = new sEndUser(m_db, m_userID);
            if(user.setPassword(args[1])) {
                return "ok changepassword";
            }
            return "error changepassword";
            
        } // If server receives the 'getmarketprice' command
        else if(args[0].equals("getmarketprice") && args.length == 3) {
            double ret = -1;
            market = new Market(m_db);
            
            if(args[1].equals("buy")) {
                ret = market.getMarketPrice(Order.OPERATION.BUY, new CurrencyPair(args[2]));
            } else if(args[1].equals("sell")) {
                ret = market.getMarketPrice(Order.OPERATION.SELL, new CurrencyPair(args[2]));
            }
            
            return "ok getmarketprice " + Double.toString(ret);
            
        } // If server receives the 'placemarketorder' command
         // Example: placemarketorder [buy/sell] [currencypair] [amount] [marketprice] [number of hours before expires]
        // Example: placemarketorder buy USD/CAD 100000 1.1 24
        else if(args[0].equals("placemarketorder") && args.length == 6) {
            market = new Market(m_db);
            
            Order o = new Order(m_userID);
            o.setType(Order.TYPE.MARKET);
            o.setStatus(Order.STATUS.PENDING);
            if(args[1].equals("buy")) {
                o.setOperation(Order.OPERATION.BUY);
            } else if(args[1].equals("sell")) {
                o.setOperation(Order.OPERATION.SELL);
            }

            // Validate currencies
            CurrencyPair currencypair = new CurrencyPair(args[2]);
            Currency currencyFrom = currencypair.getCurrencyFrom();
            Currency currencyTo = currencypair.getCurrencyTo();
            
            if(currencyFrom.getID() == -1) {
                currencyFrom.setID(m_db.getCurrencyID(currencyFrom.getName()));
            }
            if(currencyTo.getID() == -1) {
                currencyTo.setID(m_db.getCurrencyID(currencyTo.getName()));
            }
            
            o.setCurrencyPair(currencypair);
            o.setAmount(Double.valueOf(args[3]));
            o.setPrice(args[4]);
            o.setDuration(Integer.valueOf(args[5]));
            
            int orderID = market.placeOrder(o);
            
            if(orderID >= 0) {
                return "ok placemarketorder " + Integer.toString(orderID);
            }
            return "error placemarketorder";

        } // If server receives the 'editmarketorder' command
        else if(args[0].equals("editmarketorder") && args.length == 6) {
            sMarketOrder o = new sMarketOrder(m_userID);
            
            String orderID = args[1];
            String currencyPair = args[2];
            String amount = args[3];
            
            return "error editmarketorder";
            
        } // If server receives the 'placelimitorder' command
         // Example: placelimitorder [buy/sell] [currencypair] [amount] [price] [number of hours before expires] [limit (pts)]
        // Example: placelimitorder buy USD/CAD 1000 1.10 24 20
        else if(args[0].equals("placelimitorder") && args.length == 7) {
            market = new Market(m_db);
            
            Order o = new Order(m_userID);
            o.setType(Order.TYPE.LIMIT);
            o.setStatus(Order.STATUS.PENDING);
            if(args[1].equals("buy")) {
                o.setOperation(Order.OPERATION.BUY);
            } else if(args[1].equals("sell")) {
                o.setOperation(Order.OPERATION.SELL);
            }

            // Validate currencies
            CurrencyPair currencypair = new CurrencyPair(args[2]);
            Currency currencyFrom = currencypair.getCurrencyFrom();
            Currency currencyTo = currencypair.getCurrencyTo();
            
            if(currencyFrom.getID() == -1) {
                currencyFrom.setID(m_db.getCurrencyID(currencyFrom.getName()));
            }
            if(currencyTo.getID() == -1) {
                currencyTo.setID(m_db.getCurrencyID(currencyTo.getName()));
            }
            
            o.setCurrencyPair(currencypair);
            o.setAmount(Double.valueOf(args[3]));
            o.setPrice(args[4]);
            o.setDuration(Integer.valueOf(args[5]));
            o.setLimit(args[6]);
            
            int orderID = market.placeOrder(o);
            
            if(orderID >= 0) {
                return "ok placelimitorder " + Integer.toString(orderID);
            }
            return "error placelimitorder";
            
        } // If server receives the 'editlimitorder' command
        else if(args[0].equals("editlimitorder")) {
            return "error editlimitorder";
            
        } // If server receives the 'getpendingorders' command
        else if(args[0].equals("getpendingorders") && args.length == 1) {
            String ret = "";
            Vector<Integer> v = m_db.getPendingOrderIDList(m_userID);
            
            if(v != null) {
                for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
                    ret += String.valueOf((Integer)e.nextElement()) + " ";
                }
                
                return "ok getpendingorders " + ret;
            } else {
                return "error getpendingorders";
            }
            
        } // If server receives the 'getorder' command
        else if(args[0].equals("getorder") && args.length == 2) {
            try {
                Order o = m_db.getOrder(m_userID, Integer.valueOf(args[1]));
                
                return "ok getorder " + o.toString();
            } catch(Exception e) {
                e.printStackTrace();
                return "error getorder";
            }
        } // If server receives the 'getpricehistory' command
        else if(args[0].equals("getpricehistory")) {
            return "error getpricehistory";
            
        } // If server receives the 'placestoporder' command
        else if(args[0].equals("placestoporder")) {
            return "error placestoporder";
            
        } // If server receives the 'editstoporder' command
        else if(args[0].equals("editstoporder")) {
            return "error editstoporder";
            
        } // If server receives the 'placetrailingstop' command
        else if(args[0].equals("placetrailingstop")) {
            return "error placetrailingstop";
            
        } // If server receives the 'edittrailingstop' command
        else if(args[0].equals("edittrailingstop")) {
            return "error edittrailingstop";
            
        }
        
        // Invalid command
        return "error " + args[0] + (char) 13;
    }
}
