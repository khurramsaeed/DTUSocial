package grp21.dtusocial.resource.filters;

import grp21.dtusocial.model.Secured;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @url https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    final public static Key generatedKey = MacProvider.generateKey();

    /**
     * Purpose of this filter is to authenticate to client to get acccess to
     * secured resources. This is based on jason web tokens (JWTs).
     * @param requestContext
     * @throws IOException 
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Retrieve authorization header from request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("The requested resource is secured, you must provide authorization header to access it.");
        }
        
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
       
        try {
            // Validate the token
            validate(token);

        } catch (Exception e) {
            requestContext.abortWith( Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    /**
     * Validates the provided authToken
     * @param token
     * @throws Exception 
     */
    private void validate(String token) throws Exception {
        try {
            Date expiration = Jwts.parser().setSigningKey(generatedKey).parseClaimsJws(token).getBody().getExpiration();
            long expmillis = expiration.getTime();
            isTokenExpired(expmillis);

        } catch (Exception e) {
            // Token couldn't be authorized
            throw new Exception("Token couldn't be authorized");
        }
    }

    /**
     * Checks for expiration of token
     * @param expmillis
     * @throws Exception 
     */
    private void isTokenExpired(long expmillis) throws Exception {
        long nowMillis = System.currentTimeMillis();

        if (nowMillis > expmillis) {
            throw new Exception("Provided token has expired.");
        }
    }
}
