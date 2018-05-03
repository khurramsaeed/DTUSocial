package gru21.dtusocial.controller;

import grp21.dtusocial.service.data.dao.MongoTodoDAO;
import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dao.TodoDAO;
import grp21.dtusocial.service.data.dto.Todo;
import grp21.dtusocial.service.data.ElementNotFoundException;
import gru21.dtusocial.controller.TodoController;
import grp21.dtusocial.service.data.ValidException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khurram Saeed Malik
 */
public class TodoControllerImpl implements TodoController {
    TodoDAO todoDAO = new MongoTodoDAO();
    @Override
    public Todo saveTodo(Todo todo) throws PersistenceException, UnknownHostException {
        return todoDAO.save(todo);
    }

    @Override
    public List<Todo> getAllTodos() throws PersistenceException {
        return todoDAO.getAll();
    }

    @Override
    public Collection<Todo> getAllUserTodos(String id) throws PersistenceException {
        return todoDAO.findByField("userId", id);
    }

    @Override
    public Boolean updateTodo(Todo todo) throws PersistenceException, UnknownHostException {
         try {
             todoDAO.updateTodo(todo);
             return true;
        } catch (Exception e) {
            return false;
        }        
    }

    @Override
    public Boolean deleteTodo(String todoId) throws PersistenceException, ElementNotFoundException, ValidException {
        try {
        todoDAO.deleteById("todoId", todoId);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    @Override
    public List<Todo> getTodoById(String field, String id) throws PersistenceException, UnknownHostException {
        try {
            return todoDAO.findByField(field, id);
        } catch (Exception ex) {
            return null;
        }
    }
    
}
