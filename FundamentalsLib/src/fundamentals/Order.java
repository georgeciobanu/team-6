/*
 * Order.java
 *
 * Created on November 30, 2007, 7:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author GLL
 */
public class Order {
    public static enum STATUS {
        UNKNOWN,
        PENDING,
        MATCHED
    }
    
    public static enum TYPE {
        UNKNOWN,
        MARKET,
        LIMIT,
        STOPLOSS,
        TRAILINGSTOP
    }
    
    public static enum OPERATION {
        UNKNOWN,
        BUY,
        SELL
    }

    int m_orderID = -1;
    int m_userID = -1;
    STATUS m_status = STATUS.UNKNOWN;
    Timestamp m_placed = new Timestamp(0);    
    double m_amount = 0;
    TYPE m_type = TYPE.UNKNOWN;
    OPERATION m_operation = OPERATION.UNKNOWN;
    int m_duration;
    double m_price;
    CurrencyPair m_currencyPair;

    // Limit order variables
    double m_limit;
    double m_stopLoss;

    // Trailing stop variable
    double m_trailingPoints;

    /** Creates a new instance of Order */
    public Order(int userID) {
        m_userID = userID;
    }
    
    // Example: orderid 
    public Order(String content) {
        String[] args = content.split(" ");
        
        if(args.length >= 9) {
            setOrderID(args[0]);
            setStatus(args[1]);
            setPlacedDate(args[2]);
            setAmount(args[3]);
            setType(args[4]);
            setOperation(args[5]);
            setDuration(args[6]);
            setPrice(args[7]);
            setCurrencyPair(new CurrencyPair(args[8]));
            
            if(getType() == Order.TYPE.LIMIT && args.length == 10) {
                setLimit(args[9]);
            } else if(getType() == Order.TYPE.STOPLOSS && args.length == 10) {
                setStopLoss(args[9]);
            } else if(getType() == Order.TYPE.TRAILINGSTOP && args.length == 11) {
                
            }
        }
    }
    
    public int getOrderID() {
        return m_orderID;
    }
    
    public void setOrderID(int id) {
        m_orderID = id;
    }
    
    public void setOrderID(String id) {
        m_orderID = Integer.valueOf(id);
    }
    
    public int getUserID() {
        return m_userID;
    }
    
    public void setUserID(int id) {
        m_userID = id;
    }
    
    public void setUserID(String id) {
        m_userID = Integer.valueOf(id);
    }
    
    public STATUS getStatus() {
        return m_status;
    }
    
    public String getStatusString() {
        if(m_status == STATUS.PENDING) {
            return "0";
        } else if(m_status == STATUS.MATCHED) {
            return "1";
        } else {
            return "-1";
        }
    }
    
    public void setStatus(STATUS status) {
        m_status = status;
    }
    
    public void setStatus(String s) {
        if(s.equals("0")) {
            m_status = STATUS.PENDING;
        } else if(s.equals("1")) {
            m_status = STATUS.MATCHED;
        } else {
            m_status = STATUS.UNKNOWN;
        }
    }
    
    public void setStatus(int i) {
        if(i == 0) {
            m_status = STATUS.PENDING;
        } else if(i == 1) {
            m_status = STATUS.MATCHED;
        } else {
            m_status = STATUS.UNKNOWN;
        }
    }
    
    public boolean isPending() {
        return m_status == STATUS.PENDING;
    }
    
    public Timestamp getPlacedDate() {
        return m_placed;
    }
    
    public String setPlacedDateString() {
        DateTime td = new DateTime(m_placed);
        return td.getSQL();
    }
    
    public void setPlacedDate(String placed) {
        DateTime td = new DateTime(placed);
        m_placed = td.getTimestamp();
    }
    
    public void setPlacedDate(Timestamp placed) {
        m_placed = placed;
    }
    
    public double getAmount() {
        return m_amount;
    }
    
    public void setAmount(double amount) {
        m_amount = amount;
    }
    
    public void setAmount(String amount) {
        m_amount = Double.valueOf(amount);
    }
    
    public TYPE getType() {
        return m_type;
    }
    
    public String getTypeString() {
        if(m_type == TYPE.MARKET) {
            return "0";
        } else if(m_type == TYPE.LIMIT) {
            return "1";
        } else if(m_type == TYPE.STOPLOSS) {
            return "2";
        } else if(m_type == TYPE.TRAILINGSTOP) {
            return "3";
        } else {
            return "-1";
        }
    }
    
    public void setType(TYPE type) {
        m_type = type;
    }
    
    public void setType(int type) {
        if(type == 0) {
            m_type = TYPE.MARKET;
        } else if(type == 1) {
            m_type = TYPE.LIMIT;
        } else if(type == 2) {
            m_type = TYPE.STOPLOSS;
        } else if(type == 3) {
            m_type = TYPE.TRAILINGSTOP;
        } else {
            m_type = TYPE.UNKNOWN;
        }
    }
    
    public void setType(String type) {
        if(type.equals("0")) {
            m_type = TYPE.MARKET;
        } else if(type.equals("1")) {
            m_type = TYPE.LIMIT;
        } else if(type.equals("2")) {
            m_type = TYPE.STOPLOSS;
        } else if(type.equals("3")) {
            m_type = TYPE.TRAILINGSTOP;
        } else {
            m_type = TYPE.UNKNOWN;
        }
    }
    
    public OPERATION getOperation() {
        return m_operation;
    }
    
    public String getOperationString() {
        if(m_operation == OPERATION.BUY) {
            return "0";
        } else if(m_operation == OPERATION.SELL) {
            return "1";
        } else {
            return "2";
        }
    }
    
    public void setOperation(OPERATION op) {
        m_operation = op;
    }

    public void setOperation(int op) {
        if(op == 0) {
            m_operation = OPERATION.BUY;
        } else if(op == 1) {
            m_operation = OPERATION.SELL;
        } else {
            m_operation = OPERATION.UNKNOWN;
        }
    }
    
    public void setOperation(String op) {
        if(op.equals("buy")) {
            m_operation = OPERATION.BUY;
        } else if(op.equals("sell")) {
            m_operation = OPERATION.SELL;
        } else {
            m_operation = OPERATION.UNKNOWN;
        }
    }
    
    public int getDuration() {
        return m_duration;
    }
    
    public void setDuration(int duration) {
        m_duration = duration;
    }
    
    public void setDuration(String duration) {
        m_duration = Integer.valueOf(duration);
    }
    
    //return placed + duration
    public Timestamp getExpiry() {
        return m_placed;
    }
    
    public boolean hasExpired() {
        //return (new Timestamp(Now()) > m_expiry);
        return false;
    }
    
    public double getPrice() {
        return m_price;
    }
    
    public void setPrice(double price) {
        m_price = price;
    }
    
    public void setPrice(String price) {
        m_price = Double.valueOf(price);
    }
    
    public CurrencyPair getCurrencyPair() {
        return m_currencyPair;
    }
    
    public void setCurrencyPair(CurrencyPair er) {
        m_currencyPair = er;
    }
    
    public double getLimit() {
        return m_limit;
    }
    
    public void setLimit(double limit) {
        m_limit = limit;
    }
    
    public void setLimit(String limit) {
        m_limit = Double.valueOf(limit);
    }
    
    public double getStopLoss() {
        return m_stopLoss;
    }
    
    public void setStopLoss(double stoploss) {
        m_stopLoss = stoploss;
    }
    
    public void setStopLoss(String stoploss) {
        m_stopLoss = Double.valueOf(stoploss);
    }
    
    public double getTrailingPoints() {
        return m_trailingPoints;
    }
    
    public void setTrailingPoints(double trailingpoints) {
        m_trailingPoints = trailingpoints;
    }
    
    public void setTrailingPoints(String trailingpoints) {
        m_trailingPoints = Double.valueOf(trailingpoints);
    }
    
    public String toString() {
        String ret = "";
        
        int orderid = getOrderID();
        double amount = getAmount();
        int duration = getDuration();
        double price = getPrice();
        double limit = getLimit();
        double stoploss = getStopLoss();
        double trailingpoints = getTrailingPoints();
        
        CurrencyPair cp = getCurrencyPair();
        
        ret += String.valueOf(orderid) + " " + 
                getStatusString() + " " + 
                getTypeString() + " " + 
                setPlacedDateString() + " " + 
                String.valueOf(amount) + " " + 
                getTypeString() + " " + 
                getOperationString() + " " + 
                String.valueOf(duration) + " " + 
                String.valueOf(price) + " " + 
                cp.getCurrencyFrom().getName() + "/" + cp.getCurrencyTo().getName();
        
        if(getType() == Order.TYPE.LIMIT) {
            ret += String.valueOf(limit);
        } else if(getType() == Order.TYPE.STOPLOSS) {
            ret += String.valueOf(stoploss);
        } else if(getType() == Order.TYPE.TRAILINGSTOP) {
            ret += String.valueOf(trailingpoints);
        }
        
        return ret;
    }
}
