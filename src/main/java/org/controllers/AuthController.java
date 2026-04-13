package org.controllers;

import org.models.User;
import org.repository.UserRepository;
import org.utils.PasswordHasher;
import org.views.UserView;

public class AuthController {
    private final UserRepository respository;
//    private final UserView view;

    public AuthController(UserRepository respository) {
        this.respository = respository;
//        this.view = view;
    }

    public User login(String username, String password) {
        String hashpassword = PasswordHasher.hashPassword(password);
        User userDB = respository.getUserByName(username, hashpassword);
        if (userDB == null) {
            return null;
        } else if (userDB.getPassword().equals(hashpassword)) {
            return userDB;
        }
        return null;
    }

    public String authenticate(int id) {
        return respository.getOneUserWithRole(id);
    }

}
