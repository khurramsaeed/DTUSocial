package grp21.dtusocial.resource;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import brugerautorisation.transport.rmi.BrugeradminHolder;
import grp21.dtusocial.resource.auth.AuthenticationFilter;
import grp21.dtusocial.model.Credentials;
import grp21.dtusocial.service.UserDataService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *
 * @author Khurram Saeed Malik
 */

@Path("/login")
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
            
            String token = issueToken(username);
            
//            String json = new Gson().toJson(user);
//            System.out.println(json);
//            // Return the token on the response
//            System.err.println("Reached here!");

            if(userDataService.getUserById(user.brugernavn) == null) {
                userDataService.addUser(user);
                
                String url = "https://dtusocial-mank.firebaseio.com/users.json";
                final FirebaseDatabase databasereference  = FirebaseDatabase.getInstance();  
                DatabaseReference ref = databasereference.getReference(url);
                DatabaseReference userRef = ref.child("/users");
                userRef.setValueAsync(user);
            }
                        
            return Response.ok(token).header("Authorization", "Bearer " + token).build();

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
                .write("info_url", "skriv url her")
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
    
    private String issueToken(String username) {
        List<String> roller = null;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long ttlMillis = 3600000L; // to timer
        
        JwtBuilder builder =  Jwts.builder()
                .setIssuer("DTUSocial")
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, AuthenticationFilter.generatedKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }
    
}
