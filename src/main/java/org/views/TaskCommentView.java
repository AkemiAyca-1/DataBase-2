package org.views;

import org.ValidateException.ValidationException;
import org.models.TaskComment;

import java.util.List;
import java.util.Scanner;

public class TaskCommentView {

    private final Scanner scanner;

    public TaskCommentView(Scanner scanner) {
        this.scanner = scanner;
    }

    public TaskComment askCommentData() throws ValidationException {
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();

        if (title.isBlank()) {
            throw new ValidationException("El título es obligatorio.");
        }

        System.out.print("Comentario: ");
        String commentText = scanner.nextLine().trim();

        if (commentText.isBlank()) {
            throw new ValidationException("El comentario no puede estar vacío.");
        }

        System.out.print("ID Task: ");
        int idTask = readInt();
        if (idTask <= 0) {
            throw new ValidationException("ID Task inválido.");
        }

        System.out.print("ID UserWorkspace: ");
        int idUW = readInt();
        if (idUW <= 0) {
            throw new ValidationException("ID UserWorkspace inválido.");
        }

        TaskComment comment = new TaskComment();
        comment.setTitle(title);
        comment.setComment(commentText);
        comment.setIdTask(idTask);
        comment.setIdUserWorkspace(idUW);

        return comment;
    }

    public int askCommentId() {
        System.out.print("ID Comentario: ");
        return readInt();
    }

    public int askTaskId() {
        System.out.print("ID Tarea: ");
        return readInt();
    }

    public void showAll(List<TaskComment> comments) {
        if (comments.isEmpty()) {
            System.out.println("Sin comentarios.");
            return;
        }

        comments.forEach(System.out::println);
    }

    public void showSuccess(String msg) { System.out.println("Éxito: " + msg); }
    public void showError(String msg) { System.out.println("Error: " + msg); }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }
}