package org.controllers;

import org.models.User;
import org.repository.*;
import org.views.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminUserContoller extends UserController {
    public AdminUserContoller(UserRepository repository, UserView view) {
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

    public void createUser() {
        System.out.println("-- Creating user --");
        User user = view.askRegularUserData();
        if (user == null) return;
        try {
            User saved = repository.save(user);
            view.showSuccess("Usuario - " + saved.getName() + " - creado exitosamente");
        }catch (Exception ex){
            view.showError("El usuario no pudo ser creado");
        }
    }

    public void updateUser() {
        System.out.println("-- Updating mail --");
        int id = view.askId();
        if (id <= -1){
            System.out.println("El usuario no puede ser menor o igual a 0");
            return;
        }
        try {
            User userDB = repository.getUser(id);
            if (userDB == null) {
                System.out.println("No existe un usuario con id : " + id);
                return;
            }
            view.showOneUser(userDB);
            User update = view.askRegularUserData();
            if (update != null) {
                update.setId(id);
                if (repository.update(update)) view.showSuccess("Usuario actualizado correctamente");
                else view.showError("Usuario no pudo ser actualizado");
            }
        }catch (SQLException ex){
            view.showError("Error de base de datos: " + ex.getMessage());
        }catch (Exception exp){
            view.showError("El usuario no existe");
        }
    }

    public void deleteUser() {
        System.out.println("-- Deleting user --");
        int id = view.askId();
        if (id <= -1){
            System.out.println("El usuario no puede ser menor o igual a 0");
            return;
        }
        try {
            boolean delete = repository.delete(id);
            if (delete){
                view.showSuccess("Usuario eliminado correctamente");
            }
        }catch (Exception ex){
            view.showError("El usuario no existe");
        }
    }

    public void findAllUsers() {
        try {
            List<User> users = repository.findAll();
            view.showAllUsers(users);
        }catch (Exception ex){
            view.showError("No existe usuarios registrados actualmente");
        }
    }

    public void findOneUser() {
        int id = view.askId();
        if (id <= -1){
            System.out.println("El usuario no puede ser menor o igual a 0");
        }
        try {
            User user = repository.getUser(id);
            view.showOneUser(user);
        }catch (Exception ex){
            view.showError("El usuario no existe");
        }
    }

    public void findUserWithRol(){
        try {
            List<Map<String, String>> list = repository.getUsersWithRoles();
            view.showUserWithRol(list);
        }catch (Exception ex){
            view.showError("Error el mistrar la lista de los usuarios y sus roles");
        }
    }
}
