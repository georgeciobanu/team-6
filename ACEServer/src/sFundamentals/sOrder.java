/*
 * sOrder.java
 *
 * Created on November 30, 2007, 7:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sFundamentals;

import java.sql.Date;
import java.sql.Timestamp;
import fundamentals.*;

/**
 *
 * @author GLL
 */
public class sOrder extends Order{
  
    
    /**
     * Creates a new instance of sOrder
     */
    public sOrder(int userID) {
        super (userID);
        setUserID(userID);
    }        
 
}
