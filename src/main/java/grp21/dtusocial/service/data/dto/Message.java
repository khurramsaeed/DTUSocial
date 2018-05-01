package grp21.dtusocial.service.data.dto;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author Khurram Saeed Malik
 */
public class Message implements Serializable {
    private String message, interactorId;
    @Id
    private String userId;
    private long time;
    
    public String getMessage() {
        return message;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public long getTime() {
        return time;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    public String getInteractorId() {
        return interactorId;
    }
    
    public void setInteractorId(String senderId) {
        this.interactorId = senderId;
    }
    
    
    
    
}
