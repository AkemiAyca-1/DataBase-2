package org.controllers;

import org.models.Role;
import org.repository.RoleRepository;
import org.views.RoleView;

import java.util.List;

public class RolController {
    private final RoleRepository repository;
    private final RoleView view;

    public RolController(RoleRepository repository, RoleView view) {
        this.repository = repository;
        this.view = view;
    }

    public void createRole(){
        System.out.println("-- Creating role --");
        Role role = view.askDataRole();
        role = repository.saveRole(role);
    }

    public void assignRole(){
        System.out.println("-- Assigning role --");
        try {
            view.askRelationUserRol();
            view.showSuccess("Asignacion de Rol exitosa");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void findRole(){
        System.out.println("-- Finding role --");
        try {
            List<Role> roles = repository.findRoles();
            view.showAllRoles(roles);
        }catch (Exception ex){
            view.showError("No hay roles existentes");
        }
    }

    public void deleteRole(){
        System.out.println("-- Deleting role --");
        int id = view.askId();
        try {
            boolean delete = repository.deleteRole(id);
            view.showSuccess("Rol con id -"+ id +"- fue eliminado correctamente");
        }catch (Exception ex){
            view.showError("Error al eliminar rol ");
        }
    }
    public void updateRole(){
        System.out.println("-- Updating role --");
        int id = view.askId();
        String name = view.askName();
        if (id <= -1) {
            return;
        }
        try {
            repository.updateRole(id, name);
            view.showSuccess("Actualizado exitosamente");
        }catch (Exception ex){
            System.out.println("Error al actualizar rol");        }
    }

    public void updateRoleToUser(){
        System.out.println("-- Updating role --");
    }
}
