package grp21.dtusocial.resource;

import com.google.gson.Gson;
import grp21.dtusocial.model.PATCH;
import grp21.dtusocial.model.Secured;
import grp21.dtusocial.data.controller.TodoControllerImpl;
import grp21.dtusocial.service.Generator;
import grp21.dtusocial.data.PersistenceException;
import grp21.dtusocial.data.dto.Todo;
import grp21.dtusocial.data.ElementNotFoundException;
import grp21.dtusocial.data.controller.TodoController;
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
 * @author Nikolaj Holm Revised: Khurram Saeed Malik This resource personal
 * todos and todos shared with other users
 */
@Path("todos")
public class TodoRessource {

    private TodoController todoController = new TodoControllerImpl();
    String success = new Gson().toJson("Success");
    private Generator generator = Generator.getInstance();

    /**
     * Returns all todos from backend Only for test purposes
     *
     * @return
     * @throws PersistenceException
     */
    @GET
    public List<Todo> getTodos() throws PersistenceException {
        return todoController.getAllTodos();
    }

    /**
     * This method adds saves new todos added by respected clients
     *
     * @param todo
     * @return
     * @throws PersistenceException
     * @throws UnknownHostException
     */
    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTodo(Todo todo) throws PersistenceException, UnknownHostException {
        todo.setTodoId("" + generator.getGeneratedId());
        if (todo.getTodoId().length() == 14) {
            String sharedId = todo.getUserId();
            todo.setUserId("SharedResource");
            todo.setSharedId(sharedId);

            todoController.saveTodo(todo);
            return Response.ok(success).build();
        }
        todoController.saveTodo(todo);
        return Response.ok(success).build();
    }

    /**
     * Returns a todo by todoId Not implemented in frontend yet
     *
     * @param todoId
     * @return
     * @throws PersistenceException
     */
    @GET
    @Path("{todoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoById(@PathParam("todoId") String todoId) throws PersistenceException {
        try {
            List<Todo> todo = todoController.getTodoById("todoId", todoId);
            return Response.ok(todo.get(0)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }

    }

    /**
     * Updates an existing todo
     *
     * @param todo
     * @return
     * @throws PersistenceException
     * @throws ElementNotFoundException
     * @throws UnknownHostException
     */
    @PATCH
    @Path("{todoId}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchTodo(Todo todo) throws PersistenceException, ElementNotFoundException, UnknownHostException {
        System.err.println("TODOID: " + todo.getTodoId());
        try {
            todoController.updateTodo(todo);
            return Response.ok(success).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }

    }

    /**
     * Deletes a todo
     *
     * @param todoId
     * @return
     * @throws PersistenceException
     * @throws ElementNotFoundException
     */
    @DELETE
    @Path("{todoId}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTodo(@PathParam("todoId") String todoId) throws PersistenceException, ElementNotFoundException {
        try {
            todoController.deleteTodo(todoId);
            return Response.ok(success).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    /**
     * Gets shared todos with a user
     *
     * @param sharedId
     * @return
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("shared/{sharedId}")
    public Response getPersonalTodos(@PathParam("sharedId") String sharedId) throws PersistenceException {
            String sharedIdR = sharedId.subSequence(7, 14) + "" + sharedId.subSequence(0, 7);
        try {
            List<Todo> todo = todoController.getTodoById("userId", sharedId);
            List<Todo> todoR = todoController.getTodoById("userId", sharedIdR);
            todo.addAll(todoR);
            return Response.ok(todo).build();

        } catch (Exception e) {
            return Response.status(Response.Status.fromStatusCode(406)).entity(e.getMessage()).build();
        }
    }
}
