package grp21.dtusocial.service.data;
import brugerautorisation.data.Bruger;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author Khurram Saeed Malik
 */
public class MorphiaHandler {
    
    public static MorphiaHandler morphiaHandler;
    private static final String MONGODB_URI = "mongodb://heroku_zwj30pl9:gos02pdf7fpuh7v6b49thlcitg@ds223009.mlab.com:23009/heroku_zwj30pl9";
    
    private MongoClientURI uri; private MongoClient client;
    private DB db; private DBCollection coll; private MongoCollection<Document> collection ;
   
    public MorphiaHandler() throws PersistenceException, UnknownHostException {
        initializeDataStore();
    }

    private void initializeDataStore() throws PersistenceException, UnknownHostException {
        if (MONGODB_URI==null) {
            throw new PersistenceException("Environment variable: MONGODB_URI not set - contact Sysadmin");
        }
        uri = new MongoClientURI(MONGODB_URI);
        client = new MongoClient(uri);
        db = client.getDB(uri.getDatabase());
     }
    
    public void addUser(Bruger user){
    coll = db.getCollection("users");
    BasicDBObject newUser = new BasicDBObject("studyID", user.brugernavn);
     newUser.append("name", user.fornavn + " " + user.efternavn);
     coll.insert(newUser);
       System.out.println("User inserted");   
    }
    
    public void addGroup(String groupName, String[] studyNumber){
    coll = db.getCollection("groups");
    BasicDBObject newGroup = new BasicDBObject("groupName", groupName);
          for(int i = 1; studyNumber.length > i; i++){
          String groupMember = "groupMember " + i;
          newGroup.append(groupMember, studyNumber[i]);
          }
        coll.insert(newGroup);
       System.out.println("Group inserted");   
    }
    
    public void addPersonalMessage(Bruger user1, Bruger user2, String message, Date date){
    coll = db.getCollection("personalMessages");
    BasicDBObject newMessage = new BasicDBObject("Sender", user1.brugernavn);
          newMessage.append("Reciever", user2.brugernavn);
          newMessage.append("Message", message);
          newMessage.append("Sent", date);
       coll.insert(newMessage);
       System.out.println("Personal message inserted");   
    }
    
    
    public void addGroupMessage(String groupID, Bruger user, String message, Date date){
    coll = db.getCollection("groupMessages");
    BasicDBObject newMessage = new BasicDBObject("Sender", user.brugernavn);
          newMessage.append("GroupID", groupID);
          newMessage.append("Message", message);
          newMessage.append("Sent", date);
       coll.insert(newMessage);
       System.out.println("Group message inserted");   
    }
    
    public void addPersonalTodo(String todoID, String todo, String studyID, boolean done){
    coll = db.getCollection("personalTodos");
    BasicDBObject newTodo = new BasicDBObject("todoID", todoID);
          newTodo.append("userID", studyID);
          newTodo.append("todoMessage", todo);
          newTodo.append("isDone", done);
       coll.insert(newTodo);
       System.out.println("Personal todo inserted");   
    }
    
    public void addGroupTodo(String groupID, String todo){
    coll = db.getCollection("groupTodos");
    BasicDBObject newTodo = new BasicDBObject("Group", groupID);
          newTodo.append("todo", todo);
       coll.insert(newTodo);
       System.out.println("Group todo inserted");   
    }
    
    public void deleteUser(Bruger userID){
        //coll = db.getCollection("users");
        //BasicDBObject removeUser = new BasicDBObject("studyID", userID);
        collection = (MongoCollection<Document>) db.getCollection("users");
        collection.deleteMany(new Document("studyID", new ObjectId(userID.brugernavn)));
        //collection.drop();
        //coll.remove(removeUser);
       System.out.println("user removed");   
    }
    
    public void deleteGroup(String groupID){
    collection = (MongoCollection<Document>) db.getCollection("groups");
    collection.deleteOne(new Document("_id", new ObjectId(groupID)));
       System.out.println("group deleted");   
    }
    
    public void deleteTodo(String todoID){
    coll = db.getCollection("personalTodos");
    coll.remove(new BasicDBObject("todoID", todoID));
           System.out.println("personal todo deleted");   
    }
    
    
     public void getTodo(String todoID) {
        coll = db.getCollection("personalTodod");
        BasicDBObject findTodo = new BasicDBObject("todoID", todoID);
        coll.find(findTodo);
    }

    
     public void updateTodo(String todoID, String todoMSG){
    //collection = (MongoCollection<Document>) db.getCollection("groupTodos");
    coll = db.getCollection("personalTodos");
    BasicDBObject searchQuery = new BasicDBObject("todoID", todoID);
    BasicDBObject updateTodo = new BasicDBObject();
        updateTodo.append("todoMessage", todoMSG);
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateTodo);
        coll.update(searchQuery, setQuery);
       System.out.println("todo updated");   
    }

    public static MorphiaHandler getInstance() throws PersistenceException, UnknownHostException {
        if (morphiaHandler==null) morphiaHandler= new MorphiaHandler();
        return morphiaHandler;
    }

   

}