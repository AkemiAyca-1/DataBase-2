package org.models;

public abstract class User{
    private int id;
    private String name;
    private String mail;
    private String password;

    public abstract void createCategory();

    public abstract void createTask();

    public abstract void getTaskList();

    public abstract void getPendingTasks();

    public abstract void writeComment();

    public abstract void changeTaskStatus();
}
