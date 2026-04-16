package org.views;
import org.models.RegularUser;
import org.models.User;
import org.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserView {
    Scanner scanner = new Scanner(System.in);
    UserRepository repository;


    public User askRegularUserData() {
        System.out.println("Ingresar Nombre: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()){
            System.out.println("El nombre es obligatorio");
        }
        System.out.println("Ingresar Correo: ");
        String mail = scanner.nextLine().trim();
        if (mail.isBlank()){
            System.out.println("El mail es obligatorio");
        }
        System.out.println("Ingresar Contraseña: ");
        String password = scanner.nextLine().trim();
        if (password.isBlank()){
            System.out.println("La contraseña es obligatorio");
        }
        return new RegularUser(name, mail, password);
    }

    public int askId(){
        System.out.println("Ingresar Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    public String askMail(){
        System.out.println("Ingresar Correo: ");
        String mail = scanner.nextLine();
        return mail;
    }

    public void showSuccess(String message){
        System.out.println(message);
    }
    public void showError(String message){
        System.out.println(message);
    }

    public void showAllUsers(List<User> users){
        System.out.println("+----+----------------------+----------------------+");
        System.out.println("| ID | Name                 | Email                |");
        System.out.println("+----+----------------------+----------------------+");

        for (User user : users){
            System.out.printf("| %-2d | %-20s | %-20s |%n", user.getId(), user.getName(), user.getMail());
        }
        System.out.println("+----+----------------------+----------------------+");
    }

    public void showOneUser(User user){
        System.out.println("+----+----------------------+----------------------+");
        System.out.println("| ID | Name                 | Email                |");
        System.out.println("+----+----------------------+----------------------+");
        System.out.printf("| %-2d | %-20s | %-20s |%n", user.getId(), user.getName(), user.getMail());
        System.out.println("+----+----------------------+----------------------+");

    }

    public void showUserWithRol(List<Map<String, String>> data) {
        System.out.println("+--------------------+------------------------------+----------------+");
        System.out.printf("| %-20s | %-25s | %-15s |\n", "NOMBRE", "EMAIL", "ROL");
        System.out.println("+--------------------+------------------------------+----------------+");

        for (Map<String, String> reg : data) {
            System.out.printf("| %-20s | %-25s | %-15s |\n",
                    reg.get("nombre"),
                    reg.get("email"),
                    reg.get("rol"));
        }
        System.out.println("+--------------------+------------------------------+----------------+");

    }




}
