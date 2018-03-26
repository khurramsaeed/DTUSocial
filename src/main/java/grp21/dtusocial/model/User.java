package grp21.dtusocial.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Khurram Saeed Malik
 */
@XmlRootElement 
public class User {
    
    private String name, userId, password, email;
    
    public User() { 
    }
    
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    public void setId(String id) { 
        this.userId = id; 
    } 
    public void setName(String name) { 
        this.name = name; 
    } 
 
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getName() { 
        return name; 
    }
    
     public String getId() { 
        return userId; 
    } 
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
    
}
