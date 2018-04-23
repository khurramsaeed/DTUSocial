/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.resource;

import grp21.dtusocial.model.Secured;
import grp21.dtusocial.service.UserTodoService;
import grp21.dtusocial.model.Todo;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    
    private UserTodoService userTodoService = UserTodoService.getInstance();
    
    @GET 
    public List<Todo> getTodos() {
        return userTodoService.getTodos(); 
    }
    
    @GET
    @Path("{studyNr}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response getUserTodo(@PathParam("studyNr")String userId) {
        if (userTodoService.getTodoByUserId(userId) == null) {
            return Response.ok("Couldn't found any todos for user!").build();
        }
        return Response.ok(userTodoService.getTodoByUserId(userId)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTodo(Todo todo) {
        userTodoService.addTodo(todo);
        return Response.ok("Success").build();
    }
    
    
}
