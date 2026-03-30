package org.ui;

import org.entities.*;
import org.services.*;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final WorkspaceService workspaceService = new WorkspaceService();
    private final TaskService taskService = new TaskService();
    private final CategoryService categoryService = new CategoryService();
    private final CommentService commentService = new CommentService();

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
        System.out.println("Access Granted.");
        return true;
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. User Management");
            System.out.println("2. Workspace Management");
            System.out.println("3. Task Management");
            System.out.println("4. Category Management");
            System.out.println("5. Comment Management");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) break;

            switch (choice) {
                case 1 -> crudMenu("User");
                case 2 -> crudMenu("Workspace");
                case 3 -> crudMenu("Task");
                case 4 -> crudMenu("Category");
                case 5 -> crudMenu("Comment");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void crudMenu(String entityName) {
        while (true) {
            System.out.println("\n--- " + entityName.toUpperCase() + " CRUD ---");
            System.out.println("1. Create " + entityName);
            System.out.println("2. Read " + entityName);
            System.out.println("3. Update " + entityName);
            System.out.println("4. Delete " + entityName);
            System.out.println("5. List All");
            System.out.println("0. Back");
            System.out.print("Option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) break;

            executeAction(entityName, choice);
        }
    }

    private void executeAction(String entity, int action) {
        switch (entity) {
            case "User" -> {
                if (action == 1) userService.create(new RegularUser());
                if (action == 2) userService.read(1);
                if (action == 3) userService.update(new RegularUser());
                if (action == 4) userService.delete(1);
                if (action == 5) userService.listAll();
            }
            case "Workspace" -> {
                if (action == 1) workspaceService.create(new Workspace());
                if (action == 2) workspaceService.read(1);
                if (action == 3) workspaceService.update(new Workspace());
                if (action == 4) workspaceService.delete(1);
                if (action == 5) workspaceService.listAll();
            }
            case "Task" -> {
                if (action == 1) taskService.create(new Task());
                if (action == 2) taskService.read(1);
                if (action == 3) taskService.update(new Task());
                if (action == 4) taskService.delete(1);
                if (action == 5) taskService.listAll();
            }
            case "Category" -> {
                if (action == 1) categoryService.create(new Category());
                if (action == 2) categoryService.read(1);
                if (action == 3) categoryService.update(new Category());
                if (action == 4) categoryService.delete(1);
                if (action == 5) categoryService.listAll();
            }
            case "Comment" -> {
                if (action == 1) commentService.create(new Comment());
                if (action == 2) commentService.read(1);
                if (action == 3) commentService.update(new Comment());
                if (action == 4) commentService.delete(1);
                if (action == 5) commentService.listAll();
            }
        }
    }
}