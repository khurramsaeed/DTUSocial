package grp21.dtusocial.resource;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import brugerautorisation.transport.rmi.BrugeradminHolder;
import com.google.gson.Gson;
import grp21.dtusocial.model.Credentials;
import grp21.dtusocial.resource.filters.SecurityFilter;
import grp21.dtusocial.service.UserDataService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import grp21.dtusocial.service.data.*;
import static grp21.dtusocial.service.data.MorphiaHandler.morphiaHandler;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Khurram Saeed Malik
 */

@Path("login")
public class LoginResource {
    
    private final UserDataService userDataService = UserDataService.getInstance();
  
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            // Authenticate the user using the credentials provided
            Bruger user = authenticate(username, password);
            
            // Issue a token to client
            String token = issueToken(username);
            String json = new Gson().toJson(token);
            // Add user to userDataService
            if(userDataService.getUserById(user.brugernavn) == null) {
                userDataService.addUser(user); 
                morphiaHandler.addUser(user);
            }           
            return Response.ok(json).header("Authorization", "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequest() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
        final javax.json.stream.JsonGenerator gen = Json.createGenerator(writer);
        gen.writeStartObject()
                .write("message", "Requested URL doesn't exist")
                .writeEnd();
        gen.close();
        StreamingOutput stream = new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(new String(baos.toByteArray()));
                writer.flush();
            }
        };
        return Response.ok(stream).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * Authenticates user from brugerautorisation backend rmi service
     * @param username
     * @param password
     * @return
     * @throws Exception 
     */
    private Bruger authenticate(String username, String password) throws Exception {
        Brugeradmin ba = BrugeradminHolder.getBrugerAdmin();
        // returns email if success
        return ba.hentBruger(username, password);
    }
    
    /**
     * Issue token if user is successfully authenticated
     * @param username
     * @return 
     */
    private String issueToken(String username) {
        List<String> roller = null;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long ttlMillis = 3600000L; // to timer
        
        JwtBuilder builder =  Jwts.builder()
                .setIssuer("DTUSocial")
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, SecurityFilter.generatedKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }
    
}
