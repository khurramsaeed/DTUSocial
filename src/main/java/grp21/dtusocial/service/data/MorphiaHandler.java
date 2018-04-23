package grp21.dtusocial.service.data;
import brugerautorisation.data.Bruger;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author Khurram Saeed Malik
 */
public class MorphiaHandler {
    
    public static MorphiaHandler morphiaHandler;
    private static final String MONGODB_URI = "mongodb://heroku_zwj30pl9:gos02pdf7fpuh7v6b49thlcitg@ds223009.mlab.com:23009/heroku_zwj30pl9";
    
    private MongoClientURI uri; private MongoClient client;
    private DB db; private DBCollection coll;
   
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
    BasicDBObject newUser = new BasicDBObject((Map) user); 
     coll.insert(newUser);
       System.out.println("User inserted");
   
  
   
    }

    public static MorphiaHandler getInstance() throws PersistenceException, UnknownHostException {
        if (morphiaHandler==null) morphiaHandler= new MorphiaHandler();
        return morphiaHandler;
    }

}