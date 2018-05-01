package grp21.dtusocial.resource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import grp21.dtusocial.service.data.dto.Message;
import grp21.dtusocial.model.Secured;
import grp21.dtusocial.service.ChatService;
import grp21.dtusocial.service.JWTService;
import grp21.dtusocial.service.data.MorphiaHandler;
import grp21.dtusocial.service.data.PersistenceException;
import java.net.UnknownHostException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
    String success = new Gson().toJson("Success");
    private final ChatService chatService = ChatService.getInstance();
   
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessages() {
        return chatService.getMessages();
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
    public Response sendPersonalMessage(@HeaderParam("Authorization") String authHeader, Message message) {
        try {
            String userId = JWTService.resolveUser(authHeader);
            message.setUserId(userId);
            message.setTime(System.currentTimeMillis());
            
            chatService.sendMessage(message);
       
            return Response.ok(success).build();

        } catch (Exception e) {
            return Response.status(Response.Status.fromStatusCode(406)).build();
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
    public Response getPersonalChat(@HeaderParam("Authorization") String authHeader, @PathParam("chatterId") String chatterId) {
        // Retrieve senderId
        // JsonElement jsonElement = new JsonParser().parse(senderId);
        // String value = jsonElement.getAsJsonObject().get("senderId").getAsString();
        
        String username = JWTService.resolveUser(authHeader);
        List<Message> messages = chatService.getChatById(username, chatterId);
        
        return Response.ok(messages).build();
        
    }
    
   
}
