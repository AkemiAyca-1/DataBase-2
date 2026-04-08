package org.views;

import org.repository.*;
import org.controllers.*;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    private final CategoryController categoryController;
    private final TaskController taskController;
    private final AdminUserContoller adminUserController;
    private  final RolController rolController;

    public Menu(Connection connectionSQL) {
        CategoryRepository categoryRepo = new CategoryRepository(connectionSQL);
        TaskRepository taskRepo         = new TaskRepository(connectionSQL);
        UserRepository adminUserContoller = new UserRepository(connectionSQL);
        RoleRepository roleRepo        = new RoleRepository(connectionSQL);

        CategoryView categoryView = new CategoryView(scanner);
        TaskView taskView         = new TaskView(scanner);
        UserView userView = new UserView();
        RoleView roleView        = new RoleView();

        this.categoryController = new CategoryController(categoryRepo, categoryView);
        this.taskController     = new TaskController(taskRepo, taskView);
        this.adminUserController = new AdminUserContoller(adminUserContoller,userView);
        this.rolController        = new RolController(roleRepo, roleView);
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
            System.out.println("5. User Management");
            System.out.println("6. Comment Management (No disponible)");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) break;

            switch (choice) {
                case 3 -> taskMenu();
                case 4 -> categoryMenu();
                case 5 -> userMenu();
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
                case "0" -> { return; }
                default  -> System.out.println("  Opción inválida.");
            }
        }
    }

    private void userMenu() {
        System.out.println("          GESTIÓN DE USUARIOS");
        System.out.println("  1. Crear");
        System.out.println("  2. Actualizar Correo");
        System.out.println("  3. Eliminar");
        System.out.println("  4. Listar todos los usuarios");
        System.out.println("  5. Buscar por ID");
        System.out.println("  6. Crear Rol");
        System.out.println("  7. Actualizar Rol");
        System.out.println("  8. Eliminar Rol");
        System.out.println("  9. Asignar Rol");
        System.out.println("  10. Listar todos los roles existentes");
        System.out.println("  11. Actualizar Rol a Usuario");
        System.out.println("  12. Volver");
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> adminUserController.createUser();
            case 2 -> adminUserController.updateMailUser();
            case 3 -> adminUserController.deleteUser();
            case 4 -> adminUserController.findAllUsers();
            case 5 -> adminUserController.findOneUser();
            case 6 -> rolController.createRole();
            case 7 -> rolController.updateRole();
            case 8 -> rolController.deleteRole();
            case 9 -> rolController.assignRole();
            case 10 -> rolController.findRole();
            case 11 -> taskMenu();
        }
    }
}