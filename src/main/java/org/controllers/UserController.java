package org.controllers;

import org.repository.*;
import org.views.UserView;

public abstract class UserController  {
    protected UserRepository repository;
    protected UserView view;

    public UserController(UserRepository repository, UserView view){
        this.repository = repository;
        this.view = view;
    }

    public abstract void createCategory();

    public abstract void createTask();

    public abstract void getTaskList();

    public abstract void getPendingTasks();

    public abstract void writeComment();

    public abstract void changeTaskStatus();

}
