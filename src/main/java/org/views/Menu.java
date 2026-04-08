package org.views;

import org.repository.*;
import org.controllers.*;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    private final CategoryController categoryController;
    private final TaskController taskController;
//    TaskComment:
    private final TaskCommentController commentController;

    public Menu(Connection connectionSQL) {
        CategoryRepository categoryRepo = new CategoryRepository(connectionSQL);
        TaskRepository taskRepo         = new TaskRepository(connectionSQL);
        TaskCommentRepository taskCommentRepository = new TaskCommentRepository(connectionSQL);


        CategoryView categoryView = new CategoryView(scanner);
        TaskView taskView         = new TaskView(scanner);
        TaskCommentView taskCommentView = new TaskCommentView(scanner);


        this.categoryController = new CategoryController(categoryRepo, categoryView);
        this.taskController     = new TaskController(taskRepo, taskView);
        this.commentController = new TaskCommentController(taskCommentRepository, taskCommentView);
    }

    public void start() {
        if (handleAuthentication()) {
            mainMenu();
        }
    }

    private boolean handleAuthentication() {
        System.out.println("--- Login / Signup ---");
        System.out.print("Username: ");
        scanner.nextLine();
        System.out.print("Password: ");
        scanner.nextLine();
        System.out.println("Verifcación exitosa.");
        return true;
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. User Management (No disponible)");
            System.out.println("2. Workspace Management (No disponible)");
            System.out.println("3. Task Management");
            System.out.println("4. Category Management");
            System.out.println("5. Comment Management ");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = readInt();

            if (choice == 0) break;

            switch (choice) {
                case 3 -> taskMenu();
                case 4 -> categoryMenu();
                case 5 -> commentMenu();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void categoryMenu() {
        while (true) {
            System.out.println("      GESTIÓN DE CATEGORÍAS");
            System.out.println("  1. Crear");
            System.out.println("  2. Buscar por ID");
            System.out.println("  3. Actualizar");
            System.out.println("  4. Eliminar");
            System.out.println("  5. Listar todas");
            System.out.println("  6. Listar por usuario");
            System.out.println("  0. Volver");
            System.out.print("  Opción: ");

            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> categoryController.create();
                case "2" -> categoryController.readById();
                case "3" -> categoryController.update();
                case "4" -> categoryController.delete();
                case "5" -> categoryController.listAll();
                case "6" -> categoryController.listByUser();
                case "0" -> { return; }
                default  -> System.out.println("  Opción inválida.");
            }
        }
    }

    private void commentMenu() {
        while (true) {
            System.out.println("\n--- COMMENT MENU ---");
            System.out.println("1. Create Comment");
            System.out.println("2. List Comments by Task");
            System.out.println("3. Delete Comment");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) break;

            switch (choice) {
                case 1 -> commentController.create();
                case 2 -> commentController.listByTask();
                case 3 -> commentController.delete();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void taskMenu() {
        while (true) {
            System.out.println("          GESTIÓN DE TAREAS");
            System.out.println("  1. Crear");
            System.out.println("  2. Buscar por ID");
            System.out.println("  3. Actualizar");
            System.out.println("  4. Eliminar");
            System.out.println("  5. Listar todas");
            System.out.println("  6. Listar por categoría");
            System.out.println("  7. Listar por user_workspace");
//            // ------------------------
//            System.out.println("8. Crear comentario");
//            System.out.println("9. Ver comentarios por tarea");
//            System.out.println("10. Eliminar comentario");
//            // ------------------------
            System.out.println("  0. Volver");
            System.out.print("  Opción: ");

            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> taskController.create();
                case "2" -> taskController.readById();
                case "3" -> taskController.update();
                case "4" -> taskController.delete();
                case "5" -> taskController.listAll();
                case "6" -> taskController.listByCategory();
                case "7" -> taskController.listByUserWorkspace();
//                case "8" -> commentController.create();
//                case "9" -> commentController.listByTask();
//                case "10" -> commentController.delete();
                case "0" -> { return; }
                default  -> System.out.println("  Opción inválida.");
            }
        }
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}