package org.views;
import org.models.RegularUser;
import org.models.User;
import org.repository.UserRepository;
import org.utils.SecurePasswordReader;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserView {
    Scanner scanner = new Scanner(System.in);
    UserRepository repository;


    public User askRegularUserData() {
        System.out.print("Ingresar Nombre: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("Error: el nombre es obligatorio.");
            return null;
        }

        System.out.print("Ingresar Correo: ");
        String mail = scanner.nextLine().trim();
        if (!SecurePasswordReader.isEmailValid(mail)) {
            System.out.println("Error: el correo no tiene un formato válido (ejemplo: usuario@dominio.com).");
            return null;
        }

        String password = SecurePasswordReader.readPassword("Ingresar Contraseña:");
        if (password.isBlank()) {
            System.out.println("Error: la contraseña es obligatoria.");
            return null;
        }
        if (!SecurePasswordReader.isPasswordSecure(password)) {
            System.out.println("Error: la contraseña debe tener al menos 6 caracteres, " +
                    "2 mayúsculas, 2 minúsculas y 2 caracteres especiales (!@#$%...).");
            return null;
        }

        return new RegularUser(name, mail, password);
    }

    public int askId(){
        System.out.println("Ingresar Id: ");
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
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
