package grp21.dtusocial.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Khurram Saeed Malik
 */
public class Message implements Serializable {
    private String message, userId, interactorId;
    private Date time;
    
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
    
    public String getInteractorId() {
        return interactorId;
    }
    
    public void setInteractorId(String senderId) {
        this.interactorId = senderId;
    }
    
    
    
    
}
