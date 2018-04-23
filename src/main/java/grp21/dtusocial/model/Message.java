package grp21.dtusocial.model;

import java.util.Date;

/**
 *
 * @author Khurram Saeed Malik
 */
public class Message {
    private String message, receiverId, senderId;
    private Date time;
    
    public Message(String message, String receiverId, String senderId) {
        this.message = message;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.time = new Date();
       
    }  

    
    public String getMessage() {
        return message;
    }
    
    public String getReceiverId() {
        return receiverId;
    }
    
    public Date getDate() {
        return time;
    }
    
    public void setReceiverId(String userId) {
        this.receiverId = userId;
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
