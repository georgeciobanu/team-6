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
    public Order() {
    }
    
    public Order(int userID) {
        m_userID = userID;
    }
    
    // Example: orderid 
    public Order(String content) {
        String[] args = content.split(" ");
        
        if(args.length >= 10) {
            setOrderID(args[0]);
            setStatus(args[1]);
            setType(args[2]);
            setPlacedDate(args[3] + " " + args[4]);
            setAmount(args[5]);
            setOperation(args[6]);
            setDuration(args[7]);
            setPrice(args[8]);
            setCurrencyPair(new CurrencyPair(args[9]));
            
            if(getType() == Order.TYPE.LIMIT && args.length == 11) {
                setLimit(args[10]);
            } else if(getType() == Order.TYPE.STOPLOSS && args.length == 11) {
                setStopLoss(args[10]);
            } else if(getType() == Order.TYPE.TRAILINGSTOP && args.length == 12) {
                
            }
        }
    }
    
    // Input: operation is the operation performed on 'this' order, marketprice is the market price for this operation
    public boolean matches(Order order, double marketBuyPrice, double marketSellPrice) {
        Order buyingOrder = null;
        Order soldOrder = null;
        
        if(marketBuyPrice < 0 || marketBuyPrice < 0) return false;
        
        // Standardize the currency pair and operation to match them easily
        if(this.getCurrencyPair().equals(order.getCurrencyPair())) {
            order.setCurrencyPair(order.getCurrencyPair().switchPair());
            order.switchOperation();
        }
        
        // If currency pairs are the oposite of eachother and so is the operation (buy/sell)
        if(this.getCurrencyPair().matches(order.getCurrencyPair()) && getOperation() == order.getOperation()) {
            switch(getOperation()) {
                case BUY:
                    soldOrder = this;
                    buyingOrder = order;
                    break;
                case SELL:
                    soldOrder = order;
                    buyingOrder = this;
                    break;
                default:
                    return false;
            }
        }
        
        switch(buyingOrder.getType()) {
            case MARKET:
                switch(soldOrder.getType()) {
                    case MARKET: // Market buys Market
                        return true;
                    case LIMIT: // Market buys Limit
                        return (soldOrder.getLimit() >= marketBuyPrice);
                    case TRAILINGSTOP: // Market buys Trailing Stop
                        return (marketSellPrice <= soldOrder.getLimit());
                    default: // Error
                        return false;
                }
            case LIMIT:
                switch(soldOrder.getType()) {
                    case MARKET: // Limit buys Market
                        return (marketBuyPrice < buyingOrder.getLimit());
                    case LIMIT: // Limit buys Limit
                        return (marketBuyPrice < buyingOrder.getLimit()) && (marketSellPrice >= soldOrder.getStopLoss());
                    case TRAILINGSTOP: // Limit buys Trailing Stop
                        return (marketBuyPrice < buyingOrder.getLimit()) && (marketSellPrice <= soldOrder.getLimit());
                    default: // Error
                        return false;
                }
            case TRAILINGSTOP:
                switch(soldOrder.getType()) {
                    case MARKET: // Trailing Stop buys Market Order
                        return (marketBuyPrice < buyingOrder.getLimit());
                    case LIMIT: // Trailing Stop buys Limit Order
                        return (marketBuyPrice < buyingOrder.getLimit()) && (marketSellPrice >= soldOrder.getStopLoss());
                    case TRAILINGSTOP: // Trailing Stop buys Trailing Stop
                        return (marketBuyPrice < buyingOrder.getLimit()) && (marketSellPrice < soldOrder.getLimit());
                    default: // Error
                        return false;
                }
            default:
                return false;
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
    
    public String getStatusStringExplicit() {
        if(m_status == STATUS.PENDING) {
            return "pending";
        } else if(m_status == STATUS.MATCHED) {
            return "matched";
        } else {
            return "unknown";
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
    
    public String getTypeStringExplicit() {
        if(m_type == TYPE.MARKET) {
            return "market";
        } else if(m_type == TYPE.LIMIT) {
            return "limit";
        } else if(m_type == TYPE.STOPLOSS) {
            return "stop loss";
        } else if(m_type == TYPE.TRAILINGSTOP) {
            return "trailing stop";
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
    
    public String getOperationStringExplicit() {
        if(m_operation == OPERATION.BUY) {
            return "buy";
        } else if(m_operation == OPERATION.SELL) {
            return "sell";
        } else {
            return "unknown";
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
    
    public void switchOperation() {
        if(m_operation == OPERATION.BUY) {
            m_operation = OPERATION.SELL;
        } else if(m_operation == OPERATION.SELL) {
            m_operation = OPERATION.BUY;
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
                getOperationString() + " " + 
                String.valueOf(duration) + " " + 
                String.valueOf(price) + " " + 
                cp.getCurrencyFrom().getName() + "/" + cp.getCurrencyTo().getName();
        
        if(getType() == Order.TYPE.LIMIT) {
            ret += " " + String.valueOf(limit);
        } else if(getType() == Order.TYPE.STOPLOSS) {
            ret += " " + String.valueOf(stoploss);
        } else if(getType() == Order.TYPE.TRAILINGSTOP) {
            ret += " " + String.valueOf(trailingpoints);
        }
        
        return ret;
    }
}
