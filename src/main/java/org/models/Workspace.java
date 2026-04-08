package org.models;

import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private int id;
    private String name;
    private List<User> members;

    public Workspace(){
        this.members = new ArrayList<>();
    }

    public Workspace(String name){
        this.name = name;
        this.members = new ArrayList<>();
    }

    public Workspace(int id, String name){
        this.id = id;
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addUser(User user){
        if(user != null && !members.contains(user)){
            members.add(user);
        }
    }

    public void addMultipleUsers(List<User> users){
        for(User u: users){
            if(u != null && !members.contains(u)){
                members.add(u);
            }
        }
    }

    public void removeMember(User user){
        members.remove(user);
    }

    public void renameWorkspace(String newName){
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
