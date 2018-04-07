package grp21.dtusocial.resource.auth;

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
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    final public static Key generatedKey = MacProvider.generateKey();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            requestContext.abortWith( Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {
        try {
            Date exp = Jwts.parser().setSigningKey(generatedKey).parseClaimsJws(token).getBody().getExpiration();
            long expmillis = exp.getTime();
            isTokenExpired(expmillis);
            // vi kan GODT stole på denne her token

        } catch (Exception e) {
            // Vi kan IKKE stole på denne her token
            throw new Exception("Token is not authorized");
        }
    }

    private void isTokenExpired(long expmillis) throws Exception {
        long nowMillis = System.currentTimeMillis();

        if (nowMillis > expmillis) {
            throw new Exception("Token er expired");
        }
    }
}
