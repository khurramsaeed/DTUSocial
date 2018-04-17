/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.service.data;
import brugerautorisation.data.Bruger;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.net.UnknownHostException;
import java.util.Map;
/**
 *
 * @author Khurram Saeed Malik
 */
public class MorphiaHandler {
    MongoClientURI uri = new MongoClientURI("mongodb://heroku_j5dc4ld5:8qspviq97mn4194b5db31t3tr4@ds255768.mlab.com:55768/heroku_j5dc4ld5");
    MongoClient client;
    DB db = client.getDB("heroku_j5dc4ld5");
    
    DBCollection coll;

    public MorphiaHandler() throws UnknownHostException {
        this.client = new MongoClient(uri);
    }
   
    public void addUser(Bruger bruger){
    coll = db.getCollection("users");
    BasicDBObject newUser = new BasicDBObject((Map) bruger); 
    coll.insert(newUser);
       System.out.println("User inserted");
    }
}
