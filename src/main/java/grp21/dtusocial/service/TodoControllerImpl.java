package grp21.dtusocial.service;

import grp21.dtusocial.service.data.MongoTodoDAO;
import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dao.TodoDAO;
import grp21.dtusocial.service.data.dto.Todo;
import gru21.dtusocial.interfaces.ElementNotFoundException;
import gru21.dtusocial.interfaces.TodoController;
import gru21.dtusocial.interfaces.ValidException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

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
    
}
