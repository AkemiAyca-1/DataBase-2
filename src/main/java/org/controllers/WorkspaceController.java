package org.controllers;

import org.models.User;
import org.models.Workspace;
import org.models.WorkspaceSummary;
import org.repository.UserRepository;
import org.repository.WorkspaceRepository;
import org.views.WorkspaceView;

import java.sql.SQLException;
import java.util.List;

public class WorkspaceController {
    protected WorkspaceRepository workspaceRepository;
    protected UserRepository userRepository;
    protected WorkspaceView view;

    public WorkspaceController(WorkspaceRepository workspaceRepository, UserRepository userRepository, WorkspaceView view) {
        this.workspaceRepository = workspaceRepository;
        this.view = view;
    }

    public void create() {
        String name = view.askName();
        if (name.isBlank()) {
            System.out.println("error, nombre no ingresado");
            return;
        }
        try {
            Workspace saved = workspaceRepository.saveWorkspace(new Workspace(name));
            System.out.println("Workspace creado con exito " + saved.getId() + " " + saved.getName());
        } catch (SQLException e) {
            System.out.println("error al crear el workspace: " + e.getMessage());
        }
    }

    public void listAll() {
        try {
            List<Workspace> workspaces = workspaceRepository.findAll();
            view.showAllWorkspaces(workspaces);
        } catch (SQLException e) {
            System.out.println("error al listar los workspaces: " + e.getMessage());
        }
    }

    public void addMember() {
        int wsID = view.askWorkspaceId();
        int userID = view.askUserId();
        if (wsID <= 0 || userID <= 0) {
            System.out.println("Error: IDs inválidos.");
            return;
        }
        try {
            if (!workspaceRepository.existsWorkspace(wsID)) {
                System.out.println("Error: no existe un workspace con id " + wsID);
                return;
            }
            if (userRepository.getUser(userID) == null) {
                System.out.println("Error: no existe un usuario con id " + userID);
                return;
            }
            if (workspaceRepository.addMember(wsID, userID)) {
                System.out.println("El usuario fue agregado al workspace.");
            } else {
                System.out.println("Error al agregar el usuario al workspace.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteMember() {
        int wsID = view.askWorkspaceId();
        int userID = view.askUserId();
        if (wsID <= 0 || userID <= 0) {
            System.out.println("Error: IDs inválidos.");
            return;
        }
        try {
            if (workspaceRepository.deleteMember(wsID, userID)) {
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
            List<User> members = workspaceRepository.findMembersByWorkspace(wsID);
            view.showAllMembers(members);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void renameWorkspace() {
        int id = view.askWorkspaceId();
        if (id <= 0) {
            System.out.println("Error: ID de workspace inválido.");
            return;
        }
        String newName = view.askNewName();

        if (newName.isBlank()) {
            System.out.println("error, el nombre no puede estar vacio");
            return;
        }
        try {

            if (workspaceRepository.updateName(id, newName)) {
                System.out.println("Workspace renombrado correctamente a: " + newName);
            } else {
                System.out.println("error no se encontró un workspace con el Id " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void delete() {
        int id = view.askWorkspaceId();
        try {
            if (workspaceRepository.delete(id)) {
                System.out.println("Workspace eliminado correctamente");
            } else {
                System.out.println("No se encontro el workspace con ID " + id);
            }
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                System.out.println("Error: no se puede eliminar el workspace porque tiene miembros o tareas asociadas. Elimínalos primero.");
            } else {
                System.out.println("Error al eliminar workspace: " + e.getMessage());
            }
        }
    }

    public void showSummary() {
        try {
            List<WorkspaceSummary> summary = workspaceRepository.getSummary();
            view.showSummary(summary);
        } catch (SQLException e) {
            System.out.println("Error al obtener el resumen: " + e.getMessage());
        }
    }
}
