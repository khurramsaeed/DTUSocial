package grp21.dtusocial.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khurram Saeed Malik
 */
public class UserDataService {
    private List<User> usersList = new ArrayList<>();

    private static UserDataService instance = new UserDataService();

    public static UserDataService getInstance() {
        return instance;
    }

    public String addUser(User user) {
        String newId = Integer.toString(usersList.size() + 1);
        user.setId(newId);
        usersList.add(user);
        return "Added user with name " + user.getName() + " and id " + newId;
    }

    public List<User> getUsers() {  
        return usersList;
    }

    public User getUserById(String id) {
        for (User user : usersList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        // null is used for error handling
        return null;
    }
    
}
