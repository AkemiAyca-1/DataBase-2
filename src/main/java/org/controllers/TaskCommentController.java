package org.controllers;

import org.ValidateException.ValidationException;
import org.models.TaskComment;
import org.repository.TaskCommentRepository;
import org.repository.TaskRepository;
import org.repository.WorkspaceRepository;
import org.views.TaskCommentView;

import java.sql.SQLException;
import java.util.List;

public class TaskCommentController {

    private final TaskCommentRepository repository;
    private final TaskRepository taskRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TaskCommentView view;

    public TaskCommentController(TaskCommentRepository repository,
                                 TaskRepository taskRepository,
                                 WorkspaceRepository workspaceRepository,
                                 TaskCommentView view) {
        this.repository          = repository;
        this.taskRepository      = taskRepository;
        this.workspaceRepository = workspaceRepository;
        this.view                = view;
    }

    public void create() {
        try {
            TaskComment comment = view.askCommentData();
            if (taskRepository.findById(comment.getIdTask()).isEmpty()) {
                view.showError("No existe una tarea con id " + comment.getIdTask());
                return;
            }
            if (!workspaceRepository.existsUserWorkspace(comment.getIdUserWorkspace())) {
                view.showError("No existe un user_workspace con id " + comment.getIdUserWorkspace());
                return;
            }
            repository.save(comment);
            view.showSuccess("Comentario creado correctamente.");
        } catch (ValidationException | SQLException e) {
            view.showError(e.getMessage());
        }
    }

    public void listByTask() {
        int idTask = view.askTaskId();
        try {
            List<TaskComment> comments = repository.findByTask(idTask);
            view.showAll(comments);
        } catch (SQLException e) {
            view.showError(e.getMessage());
        }
    }

    public void delete() {
        int id = view.askCommentId();
        try {
            if (repository.delete(id)) {
                view.showSuccess("Comentario eliminado.");
            } else {
                view.showError("Comentario no encontrado.");
            }
        } catch (SQLException e) {
            view.showError(e.getMessage());
        }
    }
}