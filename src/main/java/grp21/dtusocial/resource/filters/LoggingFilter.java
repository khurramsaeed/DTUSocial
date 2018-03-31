package grp21.dtusocial.resource.filters;
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Khurram Saeed Malik
 * Purpose of this class is to log request & response messages
 * And to understand what can be achieved with filters
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Request filter");
		System.out.println("Headers "+requestContext.getHeaders());
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
                responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
                responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
                responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
                responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
		System.out.println("Response filter");
		System.out.println("Headers "+responseContext.getHeaders());
	}

}
