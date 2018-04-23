package grp21.dtusocial.resource;

import grp21.dtusocial.model.Message;
import grp21.dtusocial.model.Secured;
import grp21.dtusocial.service.ChatService;
import grp21.dtusocial.service.JWTService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
    
    public ChatResource() {
        Message message = new Message("Hello, this is my message", "s165162", "Jeff");
        chatService.sendMessage(message);
    }
    
    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("text/plain")
    @Path("personal")
    public Response specificChat(@HeaderParam("Authorization") String authHeader, String senderId) {
        
        String username = JWTService.getUsername(authHeader);
//        System.err.println("UserId: "+ username);
//        System.err.println("SenderId: "+ senderId);
        List<Message> messages = chatService.getChatById(username, senderId);
        return Response.ok(messages).build();
        
    }
   
}
