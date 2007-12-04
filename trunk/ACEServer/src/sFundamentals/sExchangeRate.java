/*
 * sExchangeRate.java
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

package sFundamentals;

/**
 *
 * @author GLL
 */
public class sExchangeRate {
    sCurrency m_fromCurrency;
    sCurrency m_toCurrency;
    
    /**
     * Creates a new instance of sExchangeRate
     */
    public sExchangeRate() {
    }
    
    public double getBuyPrice() {
        return 0.0;
    }
    
    public double getSellPrice() {
        return 0.0;
    }
    
    public void setFromCurrency(sCurrency from) {
        m_fromCurrency = from;
    }
    
    public sCurrency getFromCurrency() {
        return m_fromCurrency;
    }
    
    public void setToCurrency(sCurrency to) {
        m_toCurrency = to;
    }
    
    public sCurrency getToCurrency() {
        return m_toCurrency;
    }
}
