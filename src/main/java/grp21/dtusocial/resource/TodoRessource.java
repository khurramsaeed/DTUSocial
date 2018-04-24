package grp21.dtusocial.resource; 

import com.google.gson.Gson;
import grp21.dtusocial.model.PATCH;
import grp21.dtusocial.service.UserTodoService;
import grp21.dtusocial.model.Todo;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nikolaj
 */

@Path("todos")
public class TodoRessource {
    String success = new Gson().toJson("Success");
    private UserTodoService userTodoService = UserTodoService.getInstance();
    
    @GET 
    public List<Todo> getTodos() {
        return userTodoService.getTodos(); 
    }
    
     @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response addTodo(Todo todo) {
        
        userTodoService.addTodo(todo);
        return Response.ok(success).build();
    }
    
    /* @GET
    @Path("{studyNr}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response getUserTodo(@PathParam("studyNr")String userId) {
        if (userTodoService.getTodoByUserId(userId) == null) {
            return Response.ok("Couldn't found any todos for user!").build();
        }
        return Response.ok(userTodoService.getTodoByUserId(userId)).build();
    } */
    
    @GET
    @Path("{todoId}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response getUserTodo(@PathParam("todoId")String todoId) {
        try {
        return Response.ok(userTodoService.getTodoById(todoId)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
        
    }
    
    
    @PATCH
    @Path("{todoId}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response patchTodo(Todo todo) {
        System.err.println("TODOID: "+todo.getTodoId());
        try {
            userTodoService.updateTodo(todo);
        return Response.ok(success).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
        
    }
    
    
    @DELETE
    @Path("{todoId}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response deleteTodo(@PathParam("todoId")String todoId) {
        
        userTodoService.removeTodo(todoId);
        
        return Response.ok(success).build();
    }
}
