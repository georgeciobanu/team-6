/*
 * ExchangeRate.java
 *
 * Created on November 30, 2007, 7:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* TODO:
 *  getSellPrice() from database
 *  getBuyPrice() from database
 */

package fundamentals;

/**
 *
 * @author GLL
 */
public class ExchangeRate {
    Currency m_fromCurrency;
    Currency m_toCurrency;
    
    /** Creates a new instance of ExchangeRate */
    public ExchangeRate() {
    }
    
    public double getBuyPrice() {
        return 0.0;
    }
    
    public double getSellPrice() {
        return 0.0;
    }
    
    public void setFromCurrency(Currency from) {
        m_fromCurrency = from;
    }
    
    public Currency getFromCurrency() {
        return m_fromCurrency;
    }
    
    public void setToCurrency(Currency to) {
        m_toCurrency = to;
    }
    
    public Currency getToCurrency() {
        return m_toCurrency;
    }
}
