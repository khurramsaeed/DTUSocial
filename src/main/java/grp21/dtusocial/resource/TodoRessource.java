
package grp21.dtusocial.resource;

import com.google.gson.Gson;
import grp21.dtusocial.model.PATCH;
import grp21.dtusocial.model.Secured;
import gru21.dtusocial.controller.TodoControllerImpl;
import grp21.dtusocial.service.UserTodoService;
import grp21.dtusocial.service.TodoService;
import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dto.Todo;
import grp21.dtusocial.service.data.ElementNotFoundException;
import gru21.dtusocial.controller.TodoController;
import java.net.UnknownHostException;
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

    private TodoController todoController = new TodoControllerImpl();
    String success = new Gson().toJson("Success");
    private UserTodoService userTodoService = UserTodoService.getInstance();
    private TodoService todoService = TodoService.getInstance();

    @GET
    public List<Todo> getTodos() throws PersistenceException {
        return todoController.getAllTodos();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTodo(Todo todo) throws PersistenceException, UnknownHostException {
        todo.setTodoId(""+userTodoService.getGeneratedId());
        todoController.saveTodo(todo);
        // userTodoService.addTodo(todo);
        // morphiaHandler.addPersonalTodo(todo.getTodoId(), todo.getMessage(), todo.getUserId(), false);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserTodo(@PathParam("todoId") String todoId) {
        try {
            
            Todo todo = userTodoService.getTodoById(todoId);
            return Response.ok(todo).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }

    }

    @PATCH
    @Path("{todoId}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchTodo(Todo todo) throws PersistenceException, ElementNotFoundException, UnknownHostException {
        System.err.println("TODOID: " + todo.getTodoId());
        try {
            todoController.updateTodo(todo);
            //userTodoService.updateTodo(todo);
            return Response.ok(success).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }

    }

    @DELETE
    @Path("{todoId}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTodo(@PathParam("todoId") String todoId) throws PersistenceException, ElementNotFoundException {
        try {
            // userTodoService.removeTodo(todoId);
            todoController.deleteTodo(todoId);
            // morphiaHandler.deleteTodo(todoId);
            return Response.ok(success).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
    
    @PUT
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personal/{id}")
    public Response addPersonalTodo(@PathParam("id") String id, Todo todo) {
        try {
            todoService.addTodo(id, todo);
            return Response.ok(success).build();

        } catch (Exception e) {
            return Response.status(Response.Status.fromStatusCode(406)).build();
        }
    }
    
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personal/{id}")
    public Response getPersonalTodos(@PathParam("id") String id) {
        try {
            if(todoService.getTodos(id) == null) {
                Response.status(404).build();
            }
            List<Todo> todos = todoService.getTodos(id);
            return Response.ok(todos).build();

        } catch (Exception e) {
            return Response.status(Response.Status.fromStatusCode(406)).build();
        }
    }
}
