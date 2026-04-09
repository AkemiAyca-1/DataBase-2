package org.controllers;

import org.Config.ConnectionSQL;
import org.models.Task;
import org.repository.TaskRepository;
import org.repository.WorkspaceRepository;
import org.views.TaskView;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TaskController {

    private final TaskRepository repository;
    private final TaskView view;
    private final WorkspaceRepository workspaceRepository;

    public TaskController(TaskRepository repository, TaskView view, WorkspaceRepository workspaceRepository) {
        this.repository = repository;
        this.view = view;
        this.workspaceRepository = workspaceRepository;
    }

    public void create() {
        Task task = view.askTaskData();
        if (task == null) return;

        try {
            int[] ids = view.askWorkspaceMembership();
            int idUW = workspaceRepository.findUserWorkspaceId(ids[0], ids[1]);

            if (idUW == -1) {
                view.showError("El usuario no es miembro de ese workspace.");
                return;
            }

            task.setIdUserWorkspace(idUW);
            Task saved = repository.save(task);
            view.showSuccess("Tarea creada: " + saved);

        } catch (SQLException e) {
            view.showError("Error al crear la tarea: " + e.getMessage());
        }
    }

    public void readById() {
        int id = view.askId();
        try {
            Optional<Task> result = repository.findById(id);
            if (result.isPresent()) view.showTask(result.get());
            else                    view.showError("No existe una tarea con id " + id);
        } catch (SQLException e) {
            view.showError("Error al buscar: " + e.getMessage());
        }
    }

    public void update() {
        int id = view.askId();
        try {
            Optional<Task> existing = repository.findById(id);
            if (existing.isEmpty()) {
                view.showError("No existe una tarea con id " + id);
                return;
            }
            view.showTask(existing.get());
            Task updated = view.askTaskData();
            if (updated == null) return;
            updated.setIdTask(id);
            if (repository.update(updated)) view.showSuccess("Tarea actualizada: " + updated);
            else                            view.showError("No se pudo actualizar la tarea.");
        } catch (SQLException e) {
            view.showError("Error al actualizar: " + e.getMessage());
        }
    }

    public void delete() {
        int id = view.askId();
        if (!view.confirmDelete(id)) { view.showMessage("Cancelado."); return; }
        try {
            if (repository.delete(id)) view.showSuccess("Tarea " + id + " eliminada.");
            else                       view.showError("No existe una tarea con id " + id);
        } catch (SQLException e) {
            view.showError("Error al eliminar: " + e.getMessage());
        }
    }

    public void listAll() {
        try {
            List<Task> tasks = repository.findAll();
            view.showAll(tasks);
        } catch (SQLException e) {
            view.showError("Error al listar tareas: " + e.getMessage());
        }
    }

    public void listByCategory() {
        int idCategory = view.askCategoryId();
        try {
            List<Task> tasks = repository.findByCategory(idCategory);
            view.showAll(tasks);
        } catch (SQLException e) {
            view.showError("Error al filtrar por categoría: " + e.getMessage());
        }
    }

    public void listByUserWorkspace() {
        int idUW = view.askUserWorkspaceId();
        try {
            List<Task> tasks = repository.findByUserWorkspace(idUW);
            view.showAll(tasks);
        } catch (SQLException e) {
            view.showError("Error al filtrar por workspace: " + e.getMessage());
        }
    }
}