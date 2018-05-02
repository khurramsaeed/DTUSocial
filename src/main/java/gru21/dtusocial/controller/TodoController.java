package gru21.dtusocial.controller;

import grp21.dtusocial.service.data.ValidException;
import grp21.dtusocial.service.data.ElementNotFoundException;
import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dto.Todo;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Khurram Saeed Malik
 */
public interface TodoController {
    Todo saveTodo(Todo todo) throws PersistenceException, UnknownHostException;
    List<Todo> getAllTodos() throws PersistenceException;
    Collection<Todo> getAllUserTodos(String id) throws PersistenceException;
    Boolean updateTodo(Todo todo) throws PersistenceException, ElementNotFoundException, UnknownHostException;
    Boolean deleteTodo(String todoId) throws PersistenceException, ElementNotFoundException, ValidException;
       
}
