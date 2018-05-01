package grp21.dtusocial.resource;

import brugerautorisation.data.Bruger;
import com.google.gson.Gson;
import grp21.dtusocial.model.Secured;
import grp21.dtusocial.service.JWTService;
import grp21.dtusocial.service.UserDataService;
import grp21.dtusocial.service.UserTodoService;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author Khurrram Saeed Malik
 */
@Path("users")
public class UserResource {
    private final UserDataService userDataService = UserDataService.getInstance();
    private final UserTodoService userTodoService = UserTodoService.getInstance();
    /**
     * Finds all Users that exist in our backend
     * @return UsersList
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bruger> getUsers() {
        return userDataService.getUsers();
    }
    
    /**
     * Find user by id
     * @param id
     * @return User
     */
    @GET
    @Secured 
    @Path("{studyNr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("studyNr") String studyNr) {
        if (userDataService.getUserById(studyNr) == null) {
            // HTTP 404 error code
            // If User doesn't exists
           return Response.status(Response.Status.NOT_FOUND).build();  
        }
         Bruger user = userDataService.getUserById(studyNr);
         String json = new Gson().toJson(user);
         return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Find user by id
     * @param id
     * @return User
     */
    @GET
    @Path("{studyNr}/{todos}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userTodos(@PathParam("todos") String todos) {
        return  Response.ok("USERS TODOS SHOULD COME HERE").build();
    }
    
    
    /**
     * If we want to add new users
     * @param newUser
     * @return 
     */
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.TEXT_PLAIN)
//    public String addUser(User newUser) {
//        return dataService.addUser(newUser);
//    }
    /*@GET DEPRECETAD: JSON is converted with 'jersey-moxy' dependency
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        userDataService.addUser(new User("Jhon Doe", "secure", "johndoe@a.co"));
        if (userDataService.getUsers().isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        String json = new Gson().toJson(userDataService.getUsers());
        return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
    }
    */
    
    
       /**
     * Adds message to ChatService
     * @param authHeader
     * @param message
     * @return 
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("usertodo")
    public Response showUserTodos(@HeaderParam("Authorization") String authHeader) {
        try {
            String userId = JWTService.resolveUser(authHeader);
            Bruger user = userDataService.getUserById(userId);
            String json = new Gson().toJson(user);
            return Response.ok(userTodoService.getTodoByUserId(userId)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.fromStatusCode(406)).build();
        }
    }
    
}
