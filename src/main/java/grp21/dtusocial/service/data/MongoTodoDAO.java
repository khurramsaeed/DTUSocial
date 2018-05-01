package grp21.dtusocial.service.data;

import grp21.dtusocial.service.data.dao.MongoBaseDAO;
import grp21.dtusocial.service.data.dao.TodoDAO;
import grp21.dtusocial.service.data.dto.Todo;

/**
 *
 * @author Khurram Saeed Malik og Morten
 */
public class MongoTodoDAO extends MongoBaseDAO<Todo> implements TodoDAO  {
    
    public MongoTodoDAO() {
        super(Todo.class);
    }   
}