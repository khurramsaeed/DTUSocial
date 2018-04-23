package grp21.dtusocial.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Khurram Saeed Malik
 */
@Path("")
public class MainResource {
    @GET
    public Response startPage() {
        return Response.ok(""
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>DTU Social - REST API</title>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>This is DTU-Social!</h1>\n" +
                "    </body>\n" +
                "</html>\n" +
                "").build();
    }
    
}
