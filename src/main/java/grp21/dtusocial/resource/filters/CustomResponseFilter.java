package grp21.dtusocial.resource.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 *
 * @author Khurram Saeed Malik
 * This response filter fixes errors: (FRONTEND PROBLEMS, eg. Angular)
 * 1. Failed to load resource: Origin http://localhost:4200 is not allowed by Access-Control-Allow-Origin
 * 2. XMLHttpRequest cannot load http://localhost:8080/DTUSocial/login. Origin http://localhost:4200 is not allowed by Access-Control-Allow-Origin
 * 
 * What problems can this implementation have?
 * This extra http headers might open some security leaks
 * See more here: https://stackoverflow.com/questions/23450494/how-to-enable-cross-domain-requests-on-jax-rs-web-services
 */
public class CustomResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("origin");
        if (origin != null) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        }
        
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
		
    }
    
}
