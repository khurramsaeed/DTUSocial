package grp21.dtusocial.model;

public class Group {

    String groupId, groupName;
    String users[];

    public Group(String groupId, String groupName, String[] users) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.users = users;
    }
    
    public Group(){
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
    
    
    
}
