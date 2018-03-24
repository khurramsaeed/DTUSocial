package grp21.dtusocial.services;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import brugerautorisation.transport.rmi.BrugeradminHolder;
import com.google.gson.Gson;
import grp21.dtusocial.services.auth.AuthenticationFilter;
import grp21.dtusocial.services.auth.Credentials;
import grp21.dtusocial.services.auth.Secured;
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
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Khurram Saeed Malik
 */
@Secured
@Path("login")
public class LoginRestService {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            // Authenticate the user using the credentials provided
            authenticate(username, password);
            
            String token = issueToken(username);
            
//            String json = new Gson().toJson(user);
//            System.out.println(json);
//            // Return the token on the response
//            System.err.println("Reached here!");
            
            return Response.ok(token).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage() {
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
    private Bruger authenticate(String brugernavn, String password) throws Exception {
        Brugeradmin ba = BrugeradminHolder.getBrugerAdmin();
        // returns email if success
        return ba.hentBruger(brugernavn, password);
    }
    
    private String issueToken(String brugernavn) {
        List<String> roller = null;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long ttlMillis = 3600000L+3600000L; // to timer
        
        JwtBuilder builder =  Jwts.builder()
                .setIssuer("DTUSocial")
                .claim("brugernavn", brugernavn)
                .signWith(SignatureAlgorithm.HS512, AuthenticationFilter.generatedKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }
    
}
