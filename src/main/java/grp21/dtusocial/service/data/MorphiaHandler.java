package grp21.dtusocial.service.data;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import grp21.dtusocial.service.data.dto.BaseDTO;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author Khurram Saeed Malik
 */
public class MorphiaHandler {
    
    public static MorphiaHandler morphiaHandler;
    private static final String MONGODB_URI = "mongodb://heroku_zwj30pl9:gos02pdf7fpuh7v6b49thlcitg@ds223009.mlab.com:23009/heroku_zwj30pl9";
    private Morphia morphia;
    private MongoClientURI uri;
    private MongoClient client;
    //private DB db;  private DBCollection coll; private MongoCollection<Document> collection ;
    private Datastore datastore;
   
    public MorphiaHandler() throws PersistenceException, UnknownHostException {
        initializeDataStore();
    }

    private void initializeDataStore() throws PersistenceException {
        if (MONGODB_URI==null) {
            throw new PersistenceException("Environment variable: MONGODB_URI not set - contact Sysadmin");
        }
        uri = new MongoClientURI(MONGODB_URI);
        
        client = new MongoClient(uri);
        morphia = new Morphia();
        morphia.mapPackage("grp21.dtusocial.service.data.dto");
        datastore = morphia.createDatastore(client, "heroku_zwj30pl9");
        datastore.ensureIndexes();
        //db = client.getDB(uri.getDatabase());
        
     }
    
      public Datastore getDatastore() throws PersistenceException {
        if (datastore==null){
            initializeDataStore();
        }
        return datastore;
    }
     public static Datastore getDS() throws PersistenceException{
        return   getInstance().getDatastore();
        
     }
     
      public <T extends BaseDTO> T createOrUpdate(T dto) throws PersistenceException {
        getDatastore().save(dto);
        System.out.println(this.getClass() + ": dto :" + dto + dto.getId());
        return dto;
    }

      
       public <T> T getById(ObjectId objectId, Class<T> clazz) throws PersistenceException {
        return getDatastore().get(clazz,objectId);
    }

    public <T> T getById(String Id, Class<T> clazz) throws PersistenceException {

        return getDatastore().get(clazz,Id);
    }

    public <T> Boolean deleteById(ObjectId objectId, Class<T> clazz) throws PersistenceException {
        WriteResult delete = getDatastore().delete(clazz, objectId);
        return delete.getN()>=1;
    }


    public <T> List<T> getAll(Class<T> clazz){

        return datastore.find(clazz).asList();

    }
    
    
    public static MorphiaHandler getInstance() throws PersistenceException {
        if (morphiaHandler==null) try {
            morphiaHandler= new MorphiaHandler();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MorphiaHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return morphiaHandler;
    }
    
    /*
    public void addUser(Bruger user){
    coll = db.getCollection("users");
    BasicDBObject newUser = new BasicDBObject("studyID", user.brugernavn);
     newUser.append("name", user.fornavn + " " + user.efternavn);
     coll.insert(newUser);
       System.out.println("User inserted");   
    }
    
    public void addGroup(String groupName, String[] users){
    coll = db.getCollection("groups");
    BasicDBObject newGroup = new BasicDBObject("groupName", groupName);
          for(int i = 1; users.length > i; i++){
          String groupMember = "groupMember " + i;
          newGroup.append(groupMember, users[i]);
          }
        coll.insert(newGroup);
       System.out.println("Group inserted");   
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
     
    public void addPersonalChat(String userID, String message, String interactorID, long time){
    coll = db.getCollection("personalChat");
    BasicDBObject newChat = new BasicDBObject("userID", userID);
          newChat.append("interactorID", interactorID);
          newChat.append("message", message);
          newChat.append("time", time);
       coll.insert(newChat);
       System.out.println("Personal chat inserted");   
    }
    
    public List<Message> readPersonalChat(String userID, String interactorID){
    List<Message> messageList = new ArrayList<>();
    coll = db.getCollection("personalChat");
    BasicDBObject findMessagesFromUser = new BasicDBObject("userID", userID);
        DBCursor curFromUser = coll.find(findMessagesFromUser);
          while (curFromUser.hasNext()){
              curFromUser.
            messageList.add();
          }
          
   //  BasicDBObject findMessagesFromInteractor = new BasicDBObject("interactorID", interactorID);
    // DBCursor curFromInteractor = coll.find(findMessagesFromInteractor);
      //    while (curFromInteractor.hasNext()){
        //      messageList.add((Message) curFromInteractor.next());
          //}
           //if (messageList.isEmpty()) return null;
      //Add sort
       System.out.println("Personal chat inserted");   
       return messageList;
    }
    
    */

  /* public static void main(String[] args) throws PersistenceException, UnknownHostException {
      User user = new User();
      user.setName("Brian");
      user.setStudyId("s162682");
      getInstance().datastore.save(user);
        List<User> asList = getInstance().datastore.createQuery(User.class)
                .field("studyId")
                .equal("s162682")
                .asList();
        
        User userBrian = getInstance().datastore.get(User.class, "s162682");
        System.out.println(asList.get(0).getName());
        System.out.println(userBrian.getName());
   }
*/
}