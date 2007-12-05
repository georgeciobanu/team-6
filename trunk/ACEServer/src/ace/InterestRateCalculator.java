/*
 * InterestRateCalculator.java
 *
 * Created on December 3, 2007, 9:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ace;
import database.*;

/**
 *
 * @author GLL
 */
public class InterestRateCalculator implements Runnable {
    boolean m_stop;
    Thread m_currentThread;
    DBConnection m_db;
    
    /** Creates a new instance of InterestRateCalculator */
    public InterestRateCalculator(DBConnection db) {
        m_db = db;
    }
    
    public void run() {
        m_currentThread = Thread.currentThread();
        
        DBConnection.BUYSELL buy = DBConnection.BUYSELL.BUY;
        
        while(!m_stop) {
            // For all non-null balance accounts
                // Get user ID of that balance account
                // Substract with the leverage of that user for that specific currency
                // If amount is positive,
                    // Multiply amount * annualized interest rate (in function of last time charged)
                    // Add this to billing account
                // Update time of last billing charged            
                System.out.println("Calculating interest rate...");

            // Wait for a bit
            try {
                m_currentThread.sleep(10000);
            } catch(Exception e) {}
        }
    }

    public void stopMe() {
        m_stop = true;
    }
}
