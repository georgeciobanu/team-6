/*
 * SQLFormatter.java
 *
 * Created on December 5, 2007, 2:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package database;

import java.sql.*;
import fundamentals.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import java.util.Vector;

/**
 *
 * @author GLL
 */
public class SQLFormatter {
    
    /** Creates a new instance of SQLFormatter */
    public SQLFormatter() {
    }
    
    public String Now() {
        DateTime dt = new DateTime();
        return dt.getSQL();
    }
    
    public String InsertOrder(Order order) {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String date = sdf.format(order.getPlacedDate());
        String userid = String.valueOf(order.getUserID());
        String status = getString(order.getStatus());
        String placed = date; //Now(); //Time(order.getPlacedDate());
        String amount = String.valueOf(order.getAmount());
        String type = getString(order.getType());
        String operation = getString(order.getOperation());
        String duration = String.valueOf(order.getDuration());
        String price = String.valueOf(order.getPrice());
        CurrencyPair cp = order.getCurrencyPair();
        String currencyfromid = String.valueOf(cp.getCurrencyFrom().getID());
        String currencytoid = String.valueOf(cp.getCurrencyTo().getID());
        String limit = String.valueOf(order.getLimit());
        String loss = String.valueOf(order.getStopLoss());
        String trailingpoints = String.valueOf(order.getTrailingPoints());
        
        String query =
                "INSERT INTO orderpool (userid, status, placed, amount, type, operation, duration, " +
                "price, currencyfromid, currencytoid, limit, loss, trailingpoints) " +
                "VALUES (" + userid + ", " + status + ", #" + placed + "#, " + amount + ", " + type + ", " + operation + ", " + 
                duration + ", " + price + ", " + currencyfromid + ", " + currencytoid + ", " + 
                limit + ", " + loss + ", " + trailingpoints +
                ")";
        
        return query;
    }
    
    public String UpdateOrder(Order order) {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String date = sdf.format(order.getPlacedDate());
        String userid = String.valueOf(order.getUserID());
        String status = getString(order.getStatus());
        String placed = date; //Now(); //Time(order.getPlacedDate());
        String amount = String.valueOf(order.getAmount());
        String type = getString(order.getType());
        String operation = getString(order.getOperation());
        String duration = String.valueOf(order.getDuration());
        String price = String.valueOf(order.getPrice());
        CurrencyPair cp = order.getCurrencyPair();
        String currencyfromid = String.valueOf(cp.getCurrencyFrom().getID());
        String currencytoid = String.valueOf(cp.getCurrencyTo().getID());
        String limit = String.valueOf(order.getLimit());
        String loss = String.valueOf(order.getStopLoss());
        String trailingpoints = String.valueOf(order.getTrailingPoints());
        
        String orderid = String.valueOf(order.getOrderID());
        
        String query =
                "UPDATE orderpool " +
                "SET (status, placed, amount, type, operation, duration, price, currencyfromid, currencytoid, limit, loss, trailingpoints) " + 
                "VALUES (" + status + ", #" + placed + "#, " + amount + ", " + type + ",  " + operation + ", " + duration + ", " + price +
                ", " + currencyfromid + ",  " + currencytoid + ", " + limit + ", " + loss + ", " + trailingpoints + " " +
                "WHERE userid=" + userid + " " +
                "AND id=" + orderid;

//        String query =
//                "UPDATE orderpool " +
//                "SET status=" + status + ", " +
//                "placed=#" + placed + "#, " + 
//                "amount=" + amount + ", " +
//                "type=" + type + ", " +
//                "operation=" + operation + ", " +
//                "duration=" + duration + ", " +
//                "price=" + price + ", " +
//                "currencyfromid=" + currencyfromid + ", " +
//                "currencytoid=" + currencytoid + ", " +
//                "limit=" + limit + ", " +
//                "loss=" + loss + ", " +
//                "trailingpoints=" + trailingpoints + ", " +
//                "WHERE userid=" + userid;
        
        return query;
    }

    
    public String getString(Order.OPERATION operation) {
        if(operation == Order.OPERATION.BUY) {
            return "0";
        } else if(operation == Order.OPERATION.SELL) {
            return "1";
        } else {
            return "-1";
        }
    }
    
    public String getString(Order.STATUS status) {
        if(status == Order.STATUS.PENDING) {
            return "0";
        } else if(status == Order.STATUS.MATCHED) {
            return "1";
        } else {
            return "-1";
        }
    }
    
    public String getString(Order.TYPE type) {
        if(type == Order.TYPE.MARKET) {
            return "0";
        } else if(type == Order.TYPE.LIMIT) {
            return "1";
        } else if(type == Order.TYPE.STOPLOSS) {
            return "2";
        } else if(type == Order.TYPE.TRAILINGSTOP) {
            return "3";
        }else {
            return "-1";
        }
    }
    
    public String getString(DBConnection.ACCOUNTSTATUS status) {
        if(status == DBConnection.ACCOUNTSTATUS.ENABLED) {
            return "1";
        } else if(status == DBConnection.ACCOUNTSTATUS.DISABLED) {
            return "0";
        } else {
            return "-1";
        }
    }
    
}
