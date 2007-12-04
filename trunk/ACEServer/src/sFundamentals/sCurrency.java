/*
 * sCurrency.java
 *
 * Created on November 30, 2007, 7:41 PM
 *
 */

package sFundamentals;

/**
 *
 * @author GLL
 */
public class sCurrency {
    
    int m_currencyID = 0;
    String m_name = "";
    
    public sCurrency() {
    }
    
    String getName() {
        return m_name;
    }
    
    void setName(String name) {
        m_name = name;
    }
    
    int getID() {
        return m_currencyID;
    }
    
    void setID(int id) {
        m_currencyID = id;
    }
}
