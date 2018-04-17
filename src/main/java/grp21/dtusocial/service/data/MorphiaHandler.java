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
import grp21.dtusocial.model.User;
import java.util.Map;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author Khurram Saeed Malik
 */
public class MorphiaHandler {
    
    public static MorphiaHandler morphiaHandler;
    private static final String MONGODB_URI = "mongodb://heroku_j5dc4ld5:8qspviq97mn4194b5db31t3tr4@ds255768.mlab.com:55768/heroku_j5dc4ld5";
    private Morphia morphia;
    private Datastore datastore;
    private MongoClientURI uri;
    private MongoClient client;
    private DB db;
    private DBCollection coll;
   
    public void addUser(Bruger user){
    coll = db.getCollection("users");
    BasicDBObject newUser = new BasicDBObject("id", user); 
    coll.insert(newUser);
       System.out.println("User inserted");
    }
   
    private MorphiaHandler() throws PersistenceException {
        initializeDataStore();
    }

    private void initializeDataStore() throws PersistenceException {
        if (MONGODB_URI==null) {
            throw new PersistenceException("Environment variable: MONGODB_URI not set - contact Sysadmin");
        }
        this.uri = new MongoClientURI(MONGODB_URI);
        this.client = new MongoClient(uri);
        this.db = client.getDB("heroku_j5dc4ld5");;
        //morphia = new Morphia();
        // morphia.mapPackage(Config.DATA_DB_DTO);
        // datastore = morphia.createDatastore(client,getDbName(MONGODB_URI));
        // datastore.ensureIndexes();
        //ensureSuperUser();
        //For nice cleanup of MongoDB connection - make sure that morphiaHandler get garbage collected.
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> morphiaHandler=null));
    }

    private String getDbName(String mongodbUri) {
        String dbname = mongodbUri.split(":")[1];
        return dbname.substring(2);

    }

    /* private void ensureSuperUser() throws PersistenceException {
        String userString = DeployConfig.PORTAL_SUPER_USER;
        List<User> userList = datastore.createQuery(User.class)
                .field("userName").equal(userString).asList();
        RoleController roleController = ControllerRegistry.getRoleController();
        if (userList.size()<1){
            System.out.println("MorphiaHandle found no SuperUser - creating one");
            User portalAdmin = new User(userString);

            portalAdmin.getRoles().add(roleController.getPortalAdmin());
            createOrUpdate(portalAdmin);
        } else if (userList.size()>=1){
            User user = userList.get(0);
            boolean isSuperUser = false;
            for (Role role: user.getRoles()){
                if (role.getRoleName().equals(Config.PORTAL_ADMIN)){
                    isSuperUser=true;
                }
            }
            if (!isSuperUser){
                user.getRoles().add(roleController.getPortalAdmin());
                createOrUpdate(user);
            }
        }
    } */

    public static MorphiaHandler getInstance() throws PersistenceException {
        if (morphiaHandler==null) morphiaHandler= new MorphiaHandler();
        return morphiaHandler;
    }

    public Datastore getDatastore() throws PersistenceException {
        if (datastore==null){
            initializeDataStore();
        }
        return datastore;
    }

    public static Datastore getDS() throws PersistenceException {
        return getInstance().getDatastore();
    }


}
