package org.views;

import org.models.Task;
import org.enums.Status;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class TaskView {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final Scanner scanner;

    public TaskView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int askId() {
        System.out.print("  ID de la tarea: ");
        return readInt();
    }

    public int askCategoryId() {
        System.out.print("  ID de la categoría: ");
        return readInt();
    }

    public int askUserWorkspaceId() {
        System.out.print("  ID del user_workspace: ");
        return readInt();
    }

    public Task askTaskData() {
        System.out.print("  Título: ");
        String title = scanner.nextLine().trim();
        if (title.isBlank()) {
            showError("El título es obligatorio.");
            return null;
        }

        System.out.print("  Descripción (opcional): ");
        String description = scanner.nextLine().trim();

        Status status = askStatus();

        System.out.print("  ID de la categoría: ");
        int idCategory = readInt();

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setIdCategory(idCategory);
        return task;
    }

    public int[] askWorkspaceMembership() {
        System.out.print("  ID del usuario: ");
        int idUser = readInt();
        System.out.print("  ID del workspace: ");
        int idWorkspace = readInt();
        return new int[]{idUser, idWorkspace};
    }

    public boolean confirmDelete(int id) {
        System.out.printf("  ¿Eliminar tarea con id %d? (s/n): ", id);
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }

    public void showTask(Task t) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        System.out.println("\n  Tarea");
        System.out.printf("  ID              : %d%n",  t.getIdTask());
        System.out.printf("  Título          : %s%n",  t.getTitle());
        System.out.printf("  Descripción     : %s%n",  t.getDescription());
        System.out.printf("  Estado          : %s%n",  t.getStatus().getDbValue());
        System.out.printf("  Creada          : %s%n",
                t.getCreatedAt() != null ? sdf.format(t.getCreatedAt()) : "N/A");
        System.out.printf("  User Workspace  : %d%n",  t.getIdUserWorkspace());
        System.out.printf("  Categoría       : %d%n",  t.getIdCategory());
        System.out.println("  ");
    }

    public void showAll(List<Task> tasks) {
        if (tasks.isEmpty()) { System.out.println("  (Sin tareas registradas)"); return; }
        System.out.println("\n Tareas");
        System.out.printf("  %-5s %-22s %-14s %-5s %-5s%n",
                "ID", "Título", "Estado", "Cat.", "UW");
        System.out.println("  " + "─".repeat(60));
        tasks.forEach(t -> System.out.printf("  %-5d %-22s %-14s %-5d %-5d%n",
                t.getIdTask(),
                truncate(t.getTitle(), 20),
                t.getStatus().getDbValue(),
                t.getIdCategory(),
                t.getIdUserWorkspace()));
        System.out.println("  " + "─".repeat(60));
    }

    public void showSuccess(String msg) { System.out.println("Éxito: " + msg); }
    public void showError(String msg)   { System.out.println("Error: " + msg); }
    public void showMessage(String msg) { System.out.println("Info " + msg); }

    private Status askStatus() {
        System.out.println("  Estado:");
        System.out.println("    1) Pending    2) In Progress    3) Completed    4) Cancelled");
        System.out.print("  Opción (default 1): ");
        return switch (readInt()) {
            case 2 -> Status.IN_PROGRESS;
            case 3 -> Status.COMPLETED;
            case 4 -> Status.CANCELLED;
            default -> Status.PENDING;
        };
    }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}