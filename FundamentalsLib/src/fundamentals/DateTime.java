/*
 * DateTime.java
 *
 * Created on December 5, 2007, 8:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fundamentals;

import java.sql.Timestamp;
import java.sql.Date;
import fundamentals.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author GLL
 */
public class DateTime {
    
    //long m_time;
    Calendar m_time = Calendar.getInstance();
    
    // Set time to Now by default
    public DateTime() {    
    }
    
    // String in General Date format (MS Access)
    public DateTime(String time) {
        
    }
    
    public DateTime(Timestamp time) {
        setDateTime(time);
    }
    
    public DateTime(Calendar time) {
        setDateTime(time);
    }
    
    public DateTime(long time) {
        setDateTime(time);
    }
    
    public void resetNow() {
        m_time = Calendar.getInstance();
    }
    
    // String in General Date format (MS Access)
    public void setDateTime(String time) {
        
    }
    
    public void setDateTime(Timestamp time) {
        setDateTime(time.getTime());
    }
    
    public void setDateTime(Calendar time) {
        m_time = time;
    }
    
    public void setDateTime(long time) {
        m_time.setTime(new Date(time));
    }
    
    public String getSQL() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String time = sdf.format(m_time.getTime());
        return time;
    }
    
    public Timestamp getTimestamp() {
        Timestamp s = new Timestamp(getLong());
        return s;
    }
    
    public Calendar getDateTime() {
        return m_time;
    }
    
    public long getLong() {
        return m_time.getTimeInMillis();
    }
}
