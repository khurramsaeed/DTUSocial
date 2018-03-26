package grp21.dtusocial.client;

import com.google.gson.Gson;
import grp21.dtusocial.model.Credentials;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Khurram Saeed Malik
 */
public class Client {

    public static final String getUsersURL = "http://localhost:8080/DTUSocial/users";
    public static final String getSpecificUserURL = "http://localhost:8080/DTUSocial/users/1";
    public static final String loginURL = "http://localhost:8080/DTUSocial/login";
    public static String token = "";
    
    public static javax.ws.rs.client.Client client;

    public static void main(String[] args) {
        client = ClientBuilder.newClient();
        getAllUsers();
        //getSpecificUser();
        postLoginDetails();
    }

    private static void getAllUsers() {
        WebTarget target = client.target(getUsersURL);
        String s = target.request().get(String.class);
        System.out.println("All users : " + s);
    }

    private static void getSpecificUser() {
        WebTarget target = client.target(getSpecificUserURL);
        String s = target.request().header("Authorization", token).get(String.class);
        System.out.println("Got user with id: " + s);
    }

    /**
     * Returns JWT
     */
    private static void postLoginDetails() {
        WebTarget target = client.target(loginURL);
        Credentials credentials = new Credentials();
        credentials.setUsername("s165162");
        credentials.setPassword("ksm123");
        // Convert to json
        String json = new Gson().toJson(credentials);
        String response = target.request().header("Content-type", "application/json")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON),
                        String.class);
        System.out.println("Bearer " + response);

    }

}
