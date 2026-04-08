package org.controllers;

import org.repository.*;
import org.views.UserView;

public class RegularUserController extends UserController {
    public RegularUserController(UserRepository repository, UserView view) {
        super(repository, view);
    }

    @Override
    public void createCategory() {

    }

    @Override
    public void createTask() {

    }

    @Override
    public void getTaskList() {

    }

    @Override
    public void getPendingTasks() {

    }

    @Override
    public void writeComment() {

    }

    @Override
    public void changeTaskStatus() {

    }
}
