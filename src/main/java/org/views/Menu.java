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
    private final WorkspaceController workspaceController;

    private final AdminUserContoller adminUserController;
    private final RolController rolController;

    private final AuthController authController;

    public Menu(Connection connectionSQL) {
        CategoryRepository categoryRepo = new CategoryRepository(connectionSQL);
        TaskRepository taskRepo         = new TaskRepository(connectionSQL);
        TaskCommentRepository taskCommentRepository = new TaskCommentRepository(connectionSQL);
        WorkspaceRepository workspaceRepo = new WorkspaceRepository(connectionSQL);
        UserRepository UserRespository = new UserRepository(connectionSQL);
        RoleRepository roleRepo        = new RoleRepository(connectionSQL);

        CategoryView categoryView = new CategoryView(scanner);
        TaskView taskView         = new TaskView(scanner);
        TaskCommentView taskCommentView = new TaskCommentView(scanner);
        WorkspaceView workspaceView = new WorkspaceView(scanner);
        UserView userView = new UserView();
        RoleView roleView        = new RoleView(roleRepo);

        this.categoryController = new CategoryController(categoryRepo, categoryView);
        this.taskController     = new TaskController(taskRepo, taskView);
        this.commentController = new TaskCommentController(taskCommentRepository, taskCommentView);
        this.workspaceController = new WorkspaceController(workspaceRepo, workspaceView);
        this.adminUserController = new AdminUserContoller(UserRespository,userView);
        this.rolController        = new RolController(roleRepo, roleView);
        this.authController = new AuthController(UserRespository);
    }
    public void start() {
        boolean login = false;
        while (login == false) {
            if (handleAuthentication()) {
                administratorMenu();
                login = true;
            }else  {
                System.out.println("Usuario o Contraseña NO validas");
            }
        }
    }

    private boolean handleAuthentication() {
        System.out.println("--- Login / Signup ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return authController.login(username, password) ;
    }

    private void administratorMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. User Management");
            System.out.println("2. Workspace Management");
            System.out.println("3. Task Management");
            System.out.println("4. Category Management");
            System.out.println("5. Comment Management ");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = readInt();

            if (choice == 0) break;

            switch (choice) {
                case 1 -> userMenu();
                case 2 -> workspaceMenu();
                case 3 -> taskMenu();
                case 4 -> categoryMenu();
                case 5 -> commentMenu();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void generalMenu() {
        while (true) {
            System.out.println("-- MENU GENERAL --");
            System.out.println("1. Crear Categoria");
            System.out.println("2. Crear Tarea");
            System.out.println("3. Lista de Tareas");
            System.out.println("4. Listar Categorias");
            System.out.println("5. Crear Comentario");
            System.out.println("6. Actualizar Tarea");
            System.out.println("0. Salir");
            int option = readInt();
            switch (option) {
                case 1 -> categoryController.create();
                case 2 -> taskController.create();
                case 3 -> taskController.listAll();
                case 4 -> categoryController.listAll();
                case 5 -> commentController.create();
                case 6 -> taskController.update();
                case 0 -> {return;}
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

    private void workspaceMenu() {
        while (true) {
            System.out.println("\nWORKSPACES");
            System.out.println("1.Crear workspace");
            System.out.println("2.Listar todos los workspaces");
            System.out.println("3.Renombrar workspace");
            System.out.println("4.Eliminar workspace");
            System.out.println("5.Agregar un usuario");
            System.out.println("6.Eliminar miembro");
            System.out.println("7.Listar miembros");
            System.out.println("0.Volver");
            System.out.print("Opción: ");

            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> workspaceController.create();
                case "2" -> workspaceController.listAll();
                case "3" -> workspaceController.renameWorkspace();
                case "4" -> workspaceController.delete();
                case "5" -> workspaceController.addMember();
                case "6" -> workspaceController.deleteMember();
                case "7" -> workspaceController.listMembers();
                case "0" -> { return; }
                default  -> System.out.println("Opción inválida.");
            }
        }
    }

    private void userMenu() {
        while (true) {
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
//            System.out.println("  11. Actualizar Rol a Usuario");
            System.out.println("  0. Volver");
            int option = scanner.nextInt();

            switch (option) {
                case 0 -> {return;}
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