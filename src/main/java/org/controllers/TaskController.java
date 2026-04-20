package org.controllers;

import org.models.Category;
import org.models.Task;
import org.models.TaskDashboardRow;
import org.repository.CategoryRepository;
import org.repository.TaskRepository;
import org.repository.WorkspaceRepository;
import org.views.TaskView;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TaskController {

    private final TaskRepository taskRepository;
    private final WorkspaceRepository workspaceRepository;
    private final CategoryRepository categoryRepository;
    private final TaskView view;

    public TaskController(TaskRepository taskRepository,
                          WorkspaceRepository workspaceRepository,
                          CategoryRepository categoryRepository,
                          TaskView view) {
        this.taskRepository = taskRepository;
        this.workspaceRepository = workspaceRepository;
        this.categoryRepository  = categoryRepository;
        this.view                = view;
    }

    public void create() {
        Task task = view.askTaskData();
        if (task == null) return;
        try {
            if (!workspaceRepository.existsUserWorkspace(task.getIdUserWorkspace())) {
                view.showError("No existe un user_workspace con id " + task.getIdUserWorkspace());
                return;
            }
            if (task.getIdCategory() == 0) {
                Optional<Category> general = categoryRepository.findByName("General");
                if (general.isEmpty()) {
                    view.showError("No existe una categoría 'General' por defecto. Crea una primero.");
                    return;
                }
                task.setIdCategory(general.get().getIdCategory());
            } else if (categoryRepository.findById(task.getIdCategory()).isEmpty()) {
                view.showError("No existe una categoría con id " + task.getIdCategory());
                return;
            }
            Task saved = taskRepository.save(task);
            view.showSuccess("Tarea creada: " + saved);
        } catch (SQLException e) {
            view.showError("Error al crear la tarea: " + e.getMessage());
        }
    }

    public void readById() {
        int id = view.askId();
        try {
            Optional<Task> result = taskRepository.findById(id);
            if (result.isPresent()) view.showTask(result.get());
            else                    view.showError("No existe una tarea con id " + id);
        } catch (SQLException e) {
            view.showError("Error al buscar: " + e.getMessage());
        }
    }

    public void update() {
        int id = view.askId();
        try {
            Optional<Task> existing = taskRepository.findById(id);
            if (existing.isEmpty()) {
                view.showError("No existe una tarea con id " + id);
                return;
            }
            view.showTask(existing.get());
            Task updated = view.askTaskData();
            if (updated == null) return;
            if (!workspaceRepository.existsUserWorkspace(updated.getIdUserWorkspace())) {
                view.showError("No existe un user_workspace con id " + updated.getIdUserWorkspace());
                return;
            }
            if (updated.getIdCategory() == 0) {
                Optional<Category> general = categoryRepository.findByName("General");
                if (general.isEmpty()) {
                    view.showError("No existe una categoría 'General' por defecto.");
                    return;
                }
                updated.setIdCategory(general.get().getIdCategory());
            } else if (categoryRepository.findById(updated.getIdCategory()).isEmpty()) {
                view.showError("No existe una categoría con id " + updated.getIdCategory());
                return;
            }
            updated.setIdTask(id);
            if (taskRepository.update(updated)) view.showSuccess("Tarea actualizada: " + updated);
            else                            view.showError("No se pudo actualizar la tarea.");
        } catch (SQLException e) {
            view.showError("Error al actualizar: " + e.getMessage());
        }
    }

    public void delete() {
        int id = view.askId();
        if (!view.confirmDelete(id)) { view.showMessage("Cancelado."); return; }
        try {
            if (taskRepository.delete(id)) view.showSuccess("Tarea " + id + " eliminada.");
            else                       view.showError("No existe una tarea con id " + id);
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                view.showError("No se puede eliminar: la tarea tiene comentarios asociados. Elimínalos primero.");
            } else {
                view.showError("Error al eliminar: " + e.getMessage());
            }
        }
    }

    public void listAll() {
        try {
            List<Task> tasks = taskRepository.findAll();
            view.showAll(tasks);
        } catch (SQLException e) {
            view.showError("Error al listar tareas: " + e.getMessage());
        }
    }

    public void listByCategory() {
        int idCategory = view.askCategoryId();
        try {
            List<Task> tasks = taskRepository.findByCategory(idCategory);
            view.showAll(tasks);
        } catch (SQLException e) {
            view.showError("Error al filtrar por categoría: " + e.getMessage());
        }
    }

    public void listByUserWorkspace() {
        int idUW = view.askUserWorkspaceId();
        try {
            List<Task> tasks = taskRepository.findByUserWorkspace(idUW);
            view.showAll(tasks);
        } catch (SQLException e) {
            view.showError("Error al filtrar por workspace: " + e.getMessage());
        }
    }

    public void showDashboard() {
        try {
            List<TaskDashboardRow> rows = taskRepository.getDashboard();
            view.showDashboard(rows);
        } catch (SQLException e) {
            view.showError("Error al cargar el dashboard: " + e.getMessage());
        }
    }
}