package grp21.dtusocial.client;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.net.UnknownHostException;

/**
 *
 * @author Khurram Saeed Malik
 * link: http://www.mkyong.com/mongodb/java-mongodb-hello-world-example/
 * https://docs.mongodb.com/manual/reference/command/
 * http://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials/connect-to-mongodb/
 */
public class MongoClientTest {
    
    // In local dev environment this code works
    // To start in local devEnv run: mongod
    // Conclusion: WE HAVE PROBLEM WITH OUR AUTHENTICATION CREDENTIALS / HEROKU SANDBOX
    
    private static final String MONGODB_URI = "mongodb://heroku_j5dc4ld5:8qspviq97mn4194b5db31t3tr4@ds255768.mlab.com:55768/heroku_j5dc4ld5";
    
    public static void main(String[] args) throws UnknownHostException {
        MongoClientURI clientURI = new MongoClientURI(MONGODB_URI);
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        
        DB db = mongoClient.getDB("testdb");
        // boolean auth = db.authenticate("heroku_j5dc4ld5", "8qspviq97mn4194b5db31t3tr4".toCharArray());
        // System.err.println("Authenticated: " + auth);
        DBCollection coll = db.getCollection("users");
        
        BasicDBObject data = new BasicDBObject("userId", "s165162"); 
            data.append("name", "Khurram");
        
            coll.insert(data);
        System.out.println("User inserted");
    
        

    }
    
}
