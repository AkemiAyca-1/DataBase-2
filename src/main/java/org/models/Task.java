package org.models;

import java.util.Date;

import org.enums.Status;

public class Task {

    private int idTask;
    private String title;
    private String description;
    private Date createdAt;
    private Status status;
    private int idUserWorkspace;
    private int idCategory;

    public Task() {
    }

    public Task(String title, String description, Status status,
                int idUserWorkspace, int idCategory) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.idUserWorkspace = idUserWorkspace;
        this.idCategory = idCategory;
        this.createdAt = new Date();
    }

    public Task(int idTask, String title, String description, Date createdAt,
                Status status, int idUserWorkspace, int idCategory) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.idUserWorkspace = idUserWorkspace;
        this.idCategory = idCategory;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getIdUserWorkspace() {
        return idUserWorkspace;
    }

    public void setIdUserWorkspace(int id) {
        this.idUserWorkspace = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-22s | %-12s | cat: %d | uw: %d",
                idTask, title, status.getDbValue(), idCategory, idUserWorkspace);
    }
}