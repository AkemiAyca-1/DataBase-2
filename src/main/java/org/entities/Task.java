package org.entities;

import org.enums.States;

import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private Date createdAt;
    private Workspace workspace;

    public Task(){

    }

    public void updateState(States newStatus){}

    public boolean isExpired(){
        return false;
    }

    public void addComment(Comment comment){}

    public void getCommentHistory(){}

    public void updateCategory(Category category){}

    public int getRemainingDays(){return 0;}

}
