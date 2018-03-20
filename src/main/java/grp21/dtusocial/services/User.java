package grp21.dtusocial.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Khurrram Saeed Malik
 */
@Path("{user}")
public class User {
    
    @GET
    public String getUserName (@PathParam("user") String user) {
        return "Hello, " + user;
    }
    /**
     * Retrieves representation of an instance of User
     * @param first
     * @param last
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{first}-{last}")
    @Produces("text/html")
    public String getUser(  @PathParam("first") String first,
                            @PathParam("last") String last) {
        return "Hello, " + first + " " + last;
    }
    
    /* 
    @GET
    @Path("{name}")
    @Produces("text/html")
    public String getUser(  @PathParam("first") String name) {
        return "Hello, " + name;
    }
    */
}
