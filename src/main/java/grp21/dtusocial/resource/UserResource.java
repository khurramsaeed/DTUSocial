package grp21.dtusocial.resource;

import brugerautorisation.data.Bruger;
import com.google.gson.Gson;
import grp21.dtusocial.service.UserDataService;
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
    
    
}
