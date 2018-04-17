/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.service;

import java.util.ArrayList;
import java.util.List;
import grp21.dtusocial.model.Todo;

/**
 *
 * @author Nikolaj
 */
public class UserTodoService {
      private List<Todo> todoList = new ArrayList<>();
      

    private static UserTodoService instance = new UserTodoService();
    
    public static UserTodoService getInstance() {
        return instance;
    }

    public String addTodo(Todo todo) {
        todoList.add(todo);
        return "Added todo: ";
    }

    public List<Todo> getTodos() {
        return todoList;
    }

    public Todo getTodoByUserId(String userId) {
        if (todoList.isEmpty()) return null;
        
        for (Todo todo : todoList) {
            if(todo.getUserId().equals(userId)) {
                return todo;
            } else { 
            }
        }
        // null is used for error handling
        return null;
    }
    
    
}
