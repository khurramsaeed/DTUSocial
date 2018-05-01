/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.service;

import brugerautorisation.data.Bruger;
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
        
}

