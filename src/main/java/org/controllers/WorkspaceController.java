package org.controllers;

import org.models.User;
import org.models.Workspace;
import org.models.WorkspaceSummary;
import org.repository.WorkspaceRepository;
import org.views.WorkspaceView;

import java.sql.SQLException;
import java.util.List;

public class WorkspaceController {
    protected WorkspaceRepository repository;
    protected WorkspaceView view;

    public WorkspaceController(WorkspaceRepository repository, WorkspaceView view){
        this.repository = repository;
        this.view = view;
    }

    public void create(){
       String name = view.askName();
       if(name.isBlank()){
           System.out.println("error, nombre no ingresado");
           return;
       }
       try {
           Workspace saved = repository.saveWorkspace(new Workspace(name));
           System.out.println("Workspace creado con exito " + saved.getId() + " " + saved.getName());
       } catch (SQLException e) {
           System.out.println("error al crear el workspace: " + e.getMessage());
       }
    }

    public void listAll() {
        try {
            List<Workspace> workspaces = repository.findAll();
            view.showAllWorkspaces(workspaces);
        } catch (SQLException e) {
            System.out.println("error al listar los workspaces: " + e.getMessage());
        }
    }

    public void addMember() {
        int wsID = view.askWorkspaceId();
        int userID = view.askUserId();
        try {
            if (repository.addMember(wsID, userID)) {
                System.out.println("El usuario fue agregado al workspace");
            } else {
                System.out.println("Error al agregar el usuario al workspace");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void deleteMember() {
        int wsID = view.askWorkspaceId();
        int userID = view.askUserId();
        try {
            if (repository.deleteMember(wsID, userID)) {
                System.out.println("El usuario fue eliminado del workspace");
            } else {
                System.out.println("error al eliminar usuario del workspace");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void listMembers() {
        int wsID = view.askWorkspaceId();
        try {
            List<User> members = repository.findMembersByWorkspace(wsID);
            view.showAllMembers(members);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void renameWorkspace(){
        int id = view.askWorkspaceId();
        String newName = view.askNewName();

        if(newName.isBlank()){
            System.out.println("error, el nombre no puede estar vacio");
        }
        try {

            if (repository.updateName(id, newName)) {
                System.out.println("Workspace renombrado correctamente a: " + newName);
            } else {
                System.out.println("error no se encontró un workspace con el Id " + id);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void delete() {
        int id = view.askWorkspaceId();
        try {
            if (repository.delete(id)) {
                System.out.println("Workspace eliminado correctamente");
            } else {
                System.out.println("No se encontro el workspace con ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar workpace:" + e.getMessage());
        }
    }

    public void showSummary() {
        try {
            List<WorkspaceSummary> summary = repository.getSummary();
            view.showSummary(summary);
        } catch (SQLException e) {
            System.out.println("Error al obtener el resumen: " + e.getMessage());
        }
    }
}
