package grp21.dtusocial.client;

import grp21.dtusocial.services.auth.Credentials;
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
    public static javax.ws.rs.client.Client client;
    
    public static void main(String[] args) {
        client = ClientBuilder.newClient();
        getAllUsers();
        getSpecificUser();
        postLoginDetails();
    }

    private static void getAllUsers() {
        WebTarget target = client.target(getUsersURL);
        String s = target.request().get(String.class);
        System.out.println("All users : " + s);
    }

    private static void getSpecificUser() {
        WebTarget target = client.target(getSpecificUserURL);
        String s = target.request().get(String.class);
        System.out.println("Got user with id: " + s);
    }

    private static void postLoginDetails() {
        WebTarget target = client.target(loginURL);
        Credentials credentials = new Credentials();
        credentials.setUsername("s165162");
        credentials.setPassword("ksm123");
        String response = target.request()
                .post(Entity.entity(credentials, MediaType.APPLICATION_JSON),
                         String.class);
        System.out.println("Response from POST request: " + response);

    }

}
