/*
 * sBalanceAccount.java
 *
 * Created on November 23, 2007, 12:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sFundamentals;

/**
 *
 * @author GLL
 */
public class sBalanceAccount {
    private int m_userID;
    
    /**
     * Creates a new instance of sBalanceAccount
     */
    public sBalanceAccount(int userID) {
        m_userID = userID;
    }
    public int getUserID()
    {
        return m_userID;
    }
}
