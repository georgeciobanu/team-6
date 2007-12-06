/*
 * Currency.java
 *
 * Created on November 30, 2007, 7:41 PM
 *
 */

package fundamentals;

/**
 *
 * @author GLL
 */
public class Currency {
    
    int m_currencyID = -1;
    String m_name = "";
    
    public Currency() {
    }
    
    public Currency(String name) {
        m_name = name;
    }
    
    public int getID() {
        return m_currencyID;
    }
    
    public void setID(int id) {
        m_currencyID = id;
    }
    
    public String getName() {
        return m_name;
    }
    
    public void setName(String name) {
        m_name = name;
    }
}
