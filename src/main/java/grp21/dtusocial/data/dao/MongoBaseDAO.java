package grp21.dtusocial.data.dao;
import grp21.dtusocial.data.MorphiaHandler;
import grp21.dtusocial.data.PersistenceException;
import grp21.dtusocial.data.dto.BaseDTO;
import grp21.dtusocial.data.dto.Todo;
import grp21.dtusocial.data.ValidException;
import java.net.UnknownHostException;
import java.util.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author Morten og Khurram
 * @param <T>
 */
public class MongoBaseDAO<T extends BaseDTO> implements BaseDAO<T> {
    private final Class<T> type;
    //Needed for persistence
    public MongoBaseDAO(Class<T> type) {
        this.type = type;
    }

    @Override
    public T save(T element) throws PersistenceException, UnknownHostException {
        if(element!=null) element =  MorphiaHandler.getInstance().createOrUpdate(element);
        System.out.println(this.getClass() + ": element:" + element);
        return element;
    }

    /**
     * @param elements
     * Note: Removes null elements from list!
     * @return same elements - but with objectId
     */
    @Override
    public List<T> saveMultiple(List<T> elements) throws PersistenceException {
        try {
            elements.removeIf(Objects::isNull);
        } catch (UnsupportedOperationException e){
            throw new PersistenceException(e.getMessage());
        }

        MorphiaHandler.getInstance().getDatastore().save(elements);

        return elements;
    }


    @Override
    public T get(String id) throws PersistenceException, ValidException {
        try {
            ObjectId objectId = new ObjectId(id);
            return MorphiaHandler.getInstance().getById(objectId, type);
        } catch (IllegalArgumentException e){
            throw new ValidException("ObjectID not Valid: " + id);
        }
    }

    @Override
    public List<T> multiGet(Collection<String> ids) throws PersistenceException, ValidException {
        Collection<ObjectId> objectIds = new HashSet<>();

        for (String id : ids){
            try {
                objectIds.add(new ObjectId(id));
            } catch (IllegalArgumentException e){
                throw new ValidException("ObjectID not Valid:" + id);
            }
        }

        Query<T> ts = MorphiaHandler.getDS().get(type, objectIds);
        return ts.asList();
    }

    @Override
    public List<T> findByField(String fieldName, String value) throws PersistenceException {
        List<T> ts = MorphiaHandler.getDS().createQuery(type)
                .field(fieldName).equal(value).asList();
        return ts;
    }

    @Override
    public List<T> findByFields(Map<String, Object> fields) throws PersistenceException {
        Query<T> query = MorphiaHandler.getDS().createQuery(type);
        Set<Map.Entry<String, Object>> entries = fields.entrySet();
        for (Map.Entry<String,Object> entry: entries) {
            query.field(entry.getKey()).equal(entry.getValue());
        }
        return query.asList();
    }

    @Override
    public int findByFieldAndUpdateField(String findField, Object findFieldValue, String updateField, Object newValue) throws PersistenceException {
        Datastore datastore = MorphiaHandler.getDS();
        Query<T> query = datastore.createQuery(type).field(findField).equal(findFieldValue);
        UpdateOperations<T> updateOp = datastore.createUpdateOperations(type).set(updateField, newValue);
        UpdateResults updateResults = datastore.update(query, updateOp);
        return updateResults.getInsertedCount();
    }

    @Override
    public List<T> getAll() throws PersistenceException {
        return MorphiaHandler.getInstance().getAll(type) ;
    }

    @Override
    public Boolean delete(String oid) throws PersistenceException, ValidException {
        try {
            ObjectId objectId = new ObjectId(oid);
            return MorphiaHandler.getInstance().deleteById(objectId,type);
        } catch (IllegalArgumentException e){
            throw new ValidException("ObjectID not Valid: " + oid);
        }

    }
    
    @Override
    public Boolean deleteById(String fieldName, String objectId) throws PersistenceException, ValidException {
        try {
            Query<Todo> query = MorphiaHandler.getDS().createQuery(Todo.class);
            query.field(fieldName).equal(objectId);
            MorphiaHandler.getDS().findAndDelete(query);
            return true;
        } catch (IllegalArgumentException e){
            throw new ValidException("Object couldn't be deleted: deleteById()");
        }

    }
    
    
    @Override
    public Boolean updateTodo(Todo todo) throws PersistenceException, UnknownHostException {
        try {
            Query<Todo> todoQuery = MorphiaHandler.getDS().createQuery(Todo.class).field("todoId").equal(todo.getTodoId());
            UpdateOperations<Todo> operations = MorphiaHandler.getDS().createUpdateOperations(Todo.class)
                    .set("message", todo.getMessage());
            
            MorphiaHandler.getDS().update(todoQuery, operations);            
            return true;
        } catch (Exception e){
            throw new PersistenceException("Object couldn't be U: deleteById()");
        }

    }
}