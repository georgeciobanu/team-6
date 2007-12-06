/*
 * CurrencyPair.java
 *
 * Created on December 5, 2007, 12:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

/**
 *
 * @author GLL
 */
public class CurrencyPair {
    
    Currency m_fromCurrency = null;
    Currency m_toCurrency = null;
    
    /** Creates a new instance of CurrencyPair */
    public CurrencyPair() {
    }
    
    public CurrencyPair(Currency from, Currency to) {
        m_fromCurrency = from;
        m_toCurrency = to;
    }
    
    public CurrencyPair(String from, String to) {
        m_fromCurrency = new Currency(from);
        m_toCurrency = new Currency(to);
    }
    
    public CurrencyPair(String currencyPair) {
        String[] args = currencyPair.split("/");

        if(args.length == 2) {
            m_fromCurrency = new Currency(args[0]);
            m_toCurrency = new Currency(args[1]);
        }
    }
    
    public Currency getCurrencyFrom() {
        return m_fromCurrency;
    }
    
    public void setCurrencyFrom(Currency from) {
        m_fromCurrency = from;
    }
    
    public Currency getCurrencyTo() {
        return m_toCurrency;
    }
    
    public void setCurrencyTo(Currency to) {
        m_toCurrency = to;
    }
    
    public String toString() {
        return m_fromCurrency.getName() + "/" + m_toCurrency.getName();
    }
}
