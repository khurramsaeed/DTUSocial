package grp21.dtusocial.data;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import grp21.dtusocial.data.dto.BaseDTO;
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
    
}