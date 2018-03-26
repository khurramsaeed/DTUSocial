package grp21.dtusocial.resource;

import com.google.gson.Gson;
import grp21.dtusocial.model.User;
import grp21.dtusocial.resource.auth.Secured;
import grp21.dtusocial.service.UserDataService;

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
    
    /**
     * Finds all Users that exist in our backend
     * @return UsersList
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        userDataService.addUser(new User("Jhon Doe", "secure", "johndoe@a.co"));
        if (userDataService.getUsers().isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        String json = new Gson().toJson(userDataService.getUsers());
        return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Find user by id
     * @param id
     * @return User
     */
    @GET
    @Secured
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        if (userDataService.getUserById(id) == null) {
            // HTTP 404 error code
            // If User doesn't exists
           return Response.status(Response.Status.NOT_FOUND).build();  
        }
         User user = userDataService.getUserById(id);
         String json = new Gson().toJson(user);
         return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
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
    
   /* Why does this implementation doesn't work?
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUserss() {
        return userDataService.getUsers();
    }
    */
}
