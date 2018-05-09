package grp21.dtusocial.service;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import brugerautorisation.transport.rmi.BrugeradminHolder;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khurram Saeed Malik
 */
public class UserDataService {
    private List<Bruger> usersList = new ArrayList<>();

    private static UserDataService instance = new UserDataService();
    
    public UserDataService() {
        try {
            getAllUsers();
        } catch (Exception ex) {
            System.err.println("Something went wrong while retreiving users.");
        }
    }
    
    private void getAllUsers() throws Exception {
        Reader inputString = new StringReader(users);
        BufferedReader br = new BufferedReader(inputString);
        Brugeradmin ba = BrugeradminHolder.getBrugerAdmin();
 
        String userId;
        while ((userId = br.readLine()) != null) {
          System.out.println(userId);
          Bruger bruger = ba.hentBrugerOffentligt(userId);
          bruger.ekstraFelter = null;
          usersList.add(bruger);
        }
    }
    
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
    private String users = "s145005\n" +
"s153932\n" +
"s154837\n" +
"s165209\n" +
"s165228\n" +
"s144146\n" +
"s144865\n" +
"s165205\n" +
"s165208\n" +
"s165211\n" +
"s165240\n" +
"s114992\n" +
"s133996\n" +
"s144850\n" +
"s147300\n" +
"s161978\n" +
"s164925\n" +
"s114750\n" +
"s124363\n" +
"s165152\n" +
"s165153\n" +
"s165160\n" +
"s161796\n" +
"s164912\n" +
"s164926\n" +
"s164934\n" +
"s093905\n" +
"s144265\n" +
"s165202\n" +
"s165243\n" +
"s164919\n" +
"s164928\n" +
"s164930\n" +
"s143781\n" +
"s153374\n" +
"s153063\n" +
"s155634\n" +
"s160344\n" +
"s163442\n" +
"s165159\n" +
"s165161\n" +
"s143233\n" +
"s162690\n" +
"s164917\n" +
"s164924\n" +
"s133018\n" +
"s144261\n" +
"s154424\n" +
"s151641\n" +
"s155005\n" +
"s165201\n" +
"s165203\n" +
"s153558\n" +
"s165245\n" +
"s113403\n" +
"s165220\n" +
"s165232\n" +
"s165234\n" +
"s143775\n" +
"s144211\n" +
"s165206\n" +
"s165213\n" +
"s165230\n" +
"s165543\n" +
"s153795\n" +
"s164911\n" +
"s164914\n" +
"s162682\n" +
"s165151\n" +
"s165155\n" +
"s165162\n" +
"s153476\n" +
"s154674\n" +
"s165200\n" +
"s165249\n" +
"s153752\n" +
"s154989\n" +
"s164935\n" +
"s144209\n" +
"s153240\n" +
"s164921\n" +
"s133005\n" +
"s140356\n" +
"s155866\n" +
"s155384\n" +
"s164916\n" +
"s164922\n" +
"s164929\n" +
"s120052\n" +
"s136381\n" +
"s136509\n" +
"s165246\n" +
"s136615\n" +
"s145032\n" +
"s154102\n" +
"s164927\n" +
"s165227\n" +
"s165248\n" +
"s147153\n" +
"s165221\n" +
"s165223\n" +
"s165225\n" +
"s165235\n" +
"s136335\n" +
"s145918\n" +
"s165158\n" +
"s165239\n" +
"s165241\n" +
"s165242\n" +
"s122176\n" +
"manyb";
    
}