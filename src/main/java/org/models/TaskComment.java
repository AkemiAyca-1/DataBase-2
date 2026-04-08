package org.models;

import java.util.Date;

public class TaskComment {

    /*
      id_comment int auto_increment not null primary key,
    title varchar(100),
    comment text,
    comment_date date,
    id_task int not null, -- Foranea
    id_user_workspace int not null, -- Foranea
    foreign key (id_task) references task(id_task),
    foreign key (id_user_workspace) references user_workspace(id_user_workspace)
     */

    private int idComment;
    private String title;
    private String comment;
    private Date commentDate;
    private int idTask;
    private int idUserWorkspace;

    public TaskComment() {}


    public TaskComment(int idComment, String title, String comment, Date commentDate, int idTask, int idUserWorkspace) {
        this.idComment = idComment;
        this.title = title;
        this.comment = comment;
        this.commentDate = commentDate;
        this.idTask = idTask;
        this.idUserWorkspace = idUserWorkspace;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getIdUserWorkspace() {
        return idUserWorkspace;
    }

    public void setIdUserWorkspace(int idUserWorkspace) {
        this.idUserWorkspace = idUserWorkspace;
    }

    @Override
    public String toString() {
        return "TaskComment: " +
                "idComment: " + idComment +
                ", title: '" + title + '\'' +
                ", comment: '" + comment + '\'' +
                ", commentDate: " + commentDate +
                ", idTask: " + idTask +
                ", idUserWorkspace: " + idUserWorkspace;
    }
}
