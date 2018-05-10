package grp21.dtusocial.data.dao;


import grp21.dtusocial.data.dto.Todo;

/**
 *
 * @author Khurram Saeed Malik og Morten
 */
public class MongoTodoDAO extends MongoBaseDAO<Todo> implements TodoDAO  {
    
    public MongoTodoDAO() {
        super(Todo.class);
    }   
}