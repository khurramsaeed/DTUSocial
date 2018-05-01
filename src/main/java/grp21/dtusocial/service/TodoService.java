package grp21.dtusocial.service;

import grp21.dtusocial.model.Todo;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Khurram Saeed Malik
 */
public class TodoService {
    
 private HashMap<String, List<Todo>> todoList = new HashMap<>();
      private int generateId = 1010000;

    public int getGeneratedId() {
        ++generateId;
        return generateId;
    }
      

    private static TodoService instance = new TodoService();
    
    public static TodoService getInstance() {
        return instance;
    }

    public String addTodo(String id, Todo todo) {
        todo.setTodoId(getGeneratedId()+"");
        List<Todo> todos = todoList.get(id);
        todos.add(todo);
        todoList.replace(id, todos);
        return "Added todo: " + todo.getMessage();
    }
    
    public List<Todo> getTodos(String id) {
        if (todoList.isEmpty()) return null;
        List<Todo> todos = todoList.get(id);
        return todos;
    }
    
    public void removeTodo(String id, String todoId) {
        if (todoList.isEmpty()) return;
        List<Todo> todos = todoList.get(id);
        for (Todo removeTodo: todos) {
            if(removeTodo.getTodoId() == todoId) {
                todos.remove(removeTodo);
                return;
            }
        }
    }

    public Todo updateTodo(String id, Todo todo) {
        if (todoList.isEmpty()) return null;
        
        List<Todo> todos = todoList.get(id);
        for (Todo removeTodo: todos) {
            if(removeTodo.getTodoId().equals(todo.getTodoId())) {
                todos.remove(removeTodo);
                return todo;
            }
        } return null;
    }
    
    

}