package grp21.dtusocial.service.data.dto;

import brugerautorisation.data.Bruger;
import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Nikolaj
 */

public class Todo extends BaseDTO {
    @Indexed
    private String userId;
    private String message;
    @Indexed
    private String todoId;
    private boolean done;
    Bruger user;
    
    public Todo() {}
        
    public Todo (String todoId, String userId, String message, boolean done) {
        this.todoId = todoId;
        this.userId = userId;
        this.message = message;
        this.done = done;
    } 
    
    public void setTodoId(String todoId){
        this.todoId = todoId;
    }
    public String getTodoId(){
        return todoId;
    }
    
        
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setMessage(String message){
       this.message = message;
    }
    public String getMessage(){
        return message;
    }
    
     public void setDone(boolean done){
       this.done = done;
    }
    public boolean getDone(){
        return done;
    }
    
}
