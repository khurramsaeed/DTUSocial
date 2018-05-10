package grp21.dtusocial.service;

/**
 *
 * @author Nikolaj
 */
public class Generator {
      private int generateId = 1000;

    public int getGeneratedId() {
        ++generateId;
        return generateId;
    }
      

    private static Generator instance = new Generator();
    
    public static Generator getInstance() {
        return instance;
    }
    
}
