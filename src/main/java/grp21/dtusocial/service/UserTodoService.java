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
      private int generateId = 1000;

    public int getGeneratedId() {
        ++generateId;
        return generateId;
    }
      

    private static UserTodoService instance = new UserTodoService();
    
    public static UserTodoService getInstance() {
        return instance;
    }

    public String addTodo(Todo todo) {
        todo.setTodoId(getGeneratedId()+"");
        todoList.add(todo);
        return "Added todo: " + todo.getMessage();
    }

    public List<Todo> getTodos() {
        return todoList;
    }

    public List<Todo> getTodoByUserId(String userId) {
        if (todoList.isEmpty()) return null;
        
        List<Todo> userTodoList = new ArrayList();
        for (Todo todo : todoList) {
            if(todo.getUserId().equals(userId)) {
                userTodoList.add(todo);
            }
            
        } return userTodoList;
    }

    public void removeTodo(String todoId) {
        if (todoList.isEmpty()) return;
        
        for (Todo todo : todoList) {
            if(todo.getTodoId().equals(todoId)) {
                todoList.remove(todo);
                return;
            }
            
        }
    }

    public Todo getTodoById(String todoId) {
         if (todoList.isEmpty()) return null;

         
        for (Todo todo : todoList) {
            if(todo.getTodoId().equals(todoId)) {
                return todo;
            }
            
        } return null;
    }

    public Todo updateTodo(Todo todo) {
        if (todoList.isEmpty()) return null;
         
        for (Todo updateTodo : todoList) {
            if(updateTodo.getTodoId().equals(todo.getTodoId())) {
                todoList.remove(updateTodo);
                todoList.add(todo);
                return todo;
            }
            
        } return null;
        
    }
    
    
}
