/*
 * ContactInfo.java
 *
 * Created on November 23, 2007, 12:35 AM
 *
 */

package fundamentals;

/**
 *
 * @author GLL
 */
public class ContactInfo {
    int m_userID;
    String m_email;
    String m_contactInfo;
    
    /** Creates a new instance of ContactInfo */
    public ContactInfo(int userID) {
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
