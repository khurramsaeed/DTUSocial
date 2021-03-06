package grp21.dtusocial.data.dto;

import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Khurram Saeed Malik
 */
public class Message extends BaseDTO {
    private String message, interactorId;
    @Indexed
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
