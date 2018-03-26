package grp21.dtusocial.resource;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Khurram Saeed Malik
 */
@Path("chat")
public class ChatResource {
    // Generating JSON data in methods below
   
    @GET
    @Path("2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct2() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
        final javax.json.stream.JsonGenerator gen = Json.createGenerator(writer);
        gen.writeStartObject()
                .write("id", 2)
                .write("name", "Mouse")
                .write("price", 50.0)
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
    @GET
    @Path("3")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct3() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = new BufferedWriter(new OutputStreamWriter(baos));

        final JsonObject model = Json.createObjectBuilder()
                .add("id", 3)
                .add("name", "Keyboard")
                .add("price", 180.0)
                .build();
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(model.toString());
                writer.flush();
            }
        };
        return Response.ok(stream).type(MediaType.APPLICATION_JSON).build();
    }

}
