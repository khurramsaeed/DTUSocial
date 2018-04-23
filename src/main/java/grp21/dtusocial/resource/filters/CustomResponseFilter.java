package grp21.dtusocial.resource.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Khurram Saeed Malik
 * This response filter fixes errors: (FRONTEND PROBLEMS, eg. Angular)
 * 1. Failed to load resource: Origin http://localhost:4200 is not allowed by Access-Control-Allow-Origin
 * 2. XMLHttpRequest cannot load http://localhost:8080/DTUSocial/login. Origin http://localhost:4200 is not allowed by Access-Control-Allow-Origin
 * 
 * What problems can this implementation have?
 * These extra http headers might open some security leaks
 * See more here: https://stackoverflow.com/questions/23450494/how-to-enable-cross-domain-requests-on-jax-rs-web-services
 */
@Provider
public class CustomResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("origin");
        if (origin != null) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        }
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	//responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");	
    }
    
}
