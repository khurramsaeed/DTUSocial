/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.resource;

import brugerautorisation.data.Bruger;
import grp21.dtusocial.model.Group;
import grp21.dtusocial.service.GroupService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

/**
 *
 * @author Swagam
 */
@Path("groups")
public class GroupResource {
    private GroupService groupService = GroupService.getInstance();
    
    @PUT
    public void createGroup(Bruger bruger){
        
    }
         
}

