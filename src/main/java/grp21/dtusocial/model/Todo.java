/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.model;

/**
 *
 * @author Nikolaj
 */


public class Todo {
    private String todoId, message, userId;
    private boolean done;
    
    public Todo() {
        
    }
    
    User user;

        
    public Todo (String todoId, String userId, String message, boolean done) {
        this.todoId = todoId;
        this.userId = userId;
        this.message = message;
        this.done = false;
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
    public void setUserId() {
        this.userId = user.getId();
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
