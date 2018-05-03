package grp21.dtusocial.service;

import grp21.dtusocial.model.Group;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Swagam
 */
public class GroupService {
    private List<Group> groupList = new ArrayList<Group>();
    
    private static final GroupService instance = new GroupService();

    public static GroupService getInstance() {
         return instance;
    }
    
    
    public String addGroup(Group group){
        groupList.add(group);
        return "Groups added " + group;
    }
    
    public List<Group> getGroups(){
        return groupList;
    }
        
}

