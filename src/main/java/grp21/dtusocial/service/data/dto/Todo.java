/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.service.data.dto;

import brugerautorisation.data.Bruger;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author Nikolaj
 */


public class Todo {
    private String userId, message;
    @Id
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
