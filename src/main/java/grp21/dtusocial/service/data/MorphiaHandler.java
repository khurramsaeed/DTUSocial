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

/**
 *
 * @author Khurram Saeed Malik
 */
public class MorphiaHandler {
    
    public static MorphiaHandler morphiaHandler;
    private static final String MONGODB_URI = "mongodb://heroku_j5dc4ld5:8qspviq97mn4194b5db31t3tr4@ds255768.mlab.com:55768/heroku_j5dc4ld5";
    
    private MongoClientURI uri; private MongoClient client;
    private DB db; private DBCollection coll;
   
    private MorphiaHandler() throws PersistenceException {
        initializeDataStore();
    }

    private void initializeDataStore() throws PersistenceException {
        if (MONGODB_URI==null) {
            throw new PersistenceException("Environment variable: MONGODB_URI not set - contact Sysadmin");
        }
        uri = new MongoClientURI(MONGODB_URI);
        try {
            client = new MongoClient(uri);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MorphiaHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        db = client.getDB("heroku_j5dc4ld5");;
     }
    
    public void addUser(Bruger user){
    coll = db.getCollection("users");
    BasicDBObject newUser = new BasicDBObject((Map) user); 
    coll.insert(newUser);
       System.out.println("User inserted");
    }

    public static MorphiaHandler getInstance() throws PersistenceException {
        if (morphiaHandler==null) morphiaHandler= new MorphiaHandler();
        return morphiaHandler;
    }

}
