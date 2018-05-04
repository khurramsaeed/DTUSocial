package grp21.dtusocial.resource;

import com.google.gson.Gson;
import grp21.dtusocial.service.data.dto.Message;
import grp21.dtusocial.model.Secured;
import grp21.dtusocial.service.JWTService;
import grp21.dtusocial.service.data.PersistenceException;
import gru21.dtusocial.controller.MessageController;
import gru21.dtusocial.controller.MessageControllerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Khurram Saeed Malik
 */
@Path("chat")
public class ChatResource {
    private MessageController messageController = new MessageControllerImpl();
    String success = new Gson().toJson("Success");
    
    /**
     * Returns all the messages from DB
     * This method is demonstrate Chat messages
     * @return
     * @throws PersistenceException 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessages() throws PersistenceException {
        return messageController.getAllMessages();
    }
    
    /**
     * Adds message to ChatService
     * @param authHeader
     * @param message
     * @return 
     */
    @PUT
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personal")
    public Response sendPersonalMessage(@HeaderParam("Authorization") String authHeader, Message message) throws PersistenceException {
        try {
            String userId = JWTService.resolveUser(authHeader);
            message.setUserId(userId);
            message.setTime(System.currentTimeMillis());
            
            messageController.sendMessage(message);
            return Response.ok(success).build();

        } catch (Exception e) {
            // Something is wrong with DB
            // Status code: Service is not available
            return Response.status(Response.Status.fromStatusCode(503)).build();
        }
    }
    
    
    /**
     * This method consumes senderId, of whom a user wants to get messages from
     * UserId is obtained from authorisation header
     * @param authHeader
     * @param chatterId
     * @return 
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personal/{chatterId}")
    public Response getPersonalChat(@HeaderParam("Authorization") String authHeader, @PathParam("chatterId") String chatterId) throws PersistenceException {
        String username = JWTService.resolveUser(authHeader);
        // Build request for mongo: get from mongodb via multiple fields
        Map<String, Object> fields = new HashMap();
        fields.put("userId", username);
        fields.put("interactorId", chatterId);
        // Retrieve from mongodb
        List<Message> messages = messageController.getPersonalMessages(fields);
        return Response.ok(messages).build();
        
    }
    
   
}
