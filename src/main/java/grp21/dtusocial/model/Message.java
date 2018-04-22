package grp21.dtusocial.model;

import java.util.Date;

/**
 *
 * @author Khurram Saeed Malik
 */
public class Message {
    private String message, userId, senderId;
    private Date time;
    
    public Message(String message, String userId, String senderId) {
        this.message = message;
        this.userId = userId;
        this.senderId = senderId;
        this.time = new Date();
       
    }  

    
    public String getMessage() {
        return message;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public Date getDate() {
        return time;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setDate() {
        this.time = new Date();
    }
    
    public String getSenderId() {
        return senderId;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
    
    
    
}
