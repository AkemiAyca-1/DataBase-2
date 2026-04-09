package org.controllers;

import org.models.User;
import org.repository.UserRepository;
import org.views.UserView;

public class AuthController {
    private final UserRepository respository;
//    private final UserView view;

    public AuthController(UserRepository respository) {
        this.respository = respository;
//        this.view = view;
    }

    public boolean login(String username, String password) {
        User userDB = respository.getUserByName(username);
        if (userDB == null) {
            System.out.println("Usuario no encontrado");
        } else if (userDB.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
