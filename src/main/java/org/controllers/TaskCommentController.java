package org.controllers;

import org.ValidateException.ValidationException;
import org.models.TaskComment;
import org.repository.TaskCommentRepository;
import org.views.TaskCommentView;

import java.sql.SQLException;
import java.util.List;

public class TaskCommentController {

    private final TaskCommentRepository repository;
    private final TaskCommentView view;

    public TaskCommentController(TaskCommentRepository repository, TaskCommentView view) {
        this.repository = repository;
        this.view = view;
    }

    public void create() {
        try {
            TaskComment comment = view.askCommentData();
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