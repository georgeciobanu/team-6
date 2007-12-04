/*
 * sContactInfo.java
 *
 * Created on November 23, 2007, 12:35 AM
 *
 */

package sFundamentals;

/**
 *
 * @author GLL
 */
public class sContactInfo {
    int m_userID;
    String m_email;
    String m_contactInfo;
    
    /**
     * Creates a new instance of sContactInfo
     */
    public sContactInfo(int userID) {
        m_userID = userID;
    }
    
    /* Read email information of a user from the database */
    public String getEmail() {
        return "";
    }
    
    /* Write email information of a user to the database */
    public boolean setEmail(String email) {
        return false;
    }
    
    /* Read contact information of a user from the database */
    public String getContactInfo() {
        return "";
    }
    
    /* Write contact information of a user to the database */
    public boolean setContactInfo(String contactInfo) {
        return false;
    }
}
