package grp21.dtusocial.service;

import brugerautorisation.data.Bruger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khurram Saeed Malik
 */
public class UserDataService {
    private List<Bruger> usersList = new ArrayList<>();

    private static UserDataService instance = new UserDataService();
    
    public static UserDataService getInstance() {
        return instance;
    }

    public String addUser(Bruger user) {
        usersList.add(user);
        return "Added user: " + user.fornavn;  
    }

    public List<Bruger> getUsers() {
        return usersList;
    }

    public Bruger getUserById(String studyNr) {
        if (usersList.isEmpty()) return null;
        
        for (Bruger user : usersList) {
            if (user.brugernavn.equals(studyNr)) {
                return user;
            }
        }
        // null is used for error handling
        return null;
    }
    
}
