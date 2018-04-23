package grp21.dtusocial.resource;

import brugerautorisation.data.Bruger;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import grp21.dtusocial.model.Message;
import grp21.dtusocial.model.Secured;
import grp21.dtusocial.service.ChatService;
import grp21.dtusocial.service.JWTService;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Khurram Saeed Malik
 */
@Path("chat")
public class ChatResource {
    
    private final ChatService chatService = ChatService.getInstance();
    
    public ChatResource() {}
    
    
    @PUT
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personal")
    public Response sendPersonalMessage(@HeaderParam("Authorization") String authHeader, Message message) {
        try {
            String userId = JWTService.getUsername(authHeader);
            
            Message putMessage = new Message();

            putMessage.setMessage(message.getMessage());
            putMessage.setUserId(userId);
            putMessage.setInteractorId(message.getInteractorId());
            putMessage.setDate();
            
            chatService.sendMessage(putMessage);
            
            return Response.ok("success").build();

        } catch (Exception e) {
            return Response.status(Response.Status.fromStatusCode(406)).build();
        }
    }
    
    
    /**
     * This method consumes senderId, of whom a user wants to get messages from
     * UserId is obtained from authorisation header
     * @param authHeader
     * @param senderId
     * @return 
     */
    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personal")
    public Response getPersonalChat(@HeaderParam("Authorization") String authHeader, String senderId) {
        // Retrieve senderId
        JsonElement jsonElement = new JsonParser().parse(senderId);
        String value = jsonElement.getAsJsonObject().get("senderId").getAsString();
        
        String username = JWTService.getUsername(authHeader);
        List<Message> messages = chatService.getChatById(username, value);
        return Response.ok(messages).build();
        
    }
    
   
}
