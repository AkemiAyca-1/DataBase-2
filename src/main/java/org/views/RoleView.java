package org.views;
import com.google.protobuf.Message;
import org.models.*;
import org.repository.RoleRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RoleView {
    RoleRepository roleRepository;
    Scanner scanner = new Scanner(System.in);

    public RoleView(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role askDataRole(){
        System.out.println("Ingrese el nombre del nuevo Rol: ");
        String name = scanner.nextLine();

        Role role = new Role(name);
        return role;
    }

    public int askId(){
        System.out.println("Ingrese el id del rol: ");
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
    public String askName(){
        System.out.println("Ingrese el nombre del rol: ");
        String name = scanner.nextLine();
        return name;
    }

    public void askRelationUserRol() throws SQLException {
        System.out.println("Ingrese el id del Usuario a asignar rol: ");
        int id_usuario = scanner.nextInt();
        System.out.println("Ingrese el id del rol a asignar: ");
        int id_rol = scanner.nextInt();
        roleRepository.assignRole(id_rol,id_usuario);
    }

    public void showAllRoles(List<Role> roles){
        System.out.println("+----+----------------------+");
        System.out.println("| ID | Name                 |");
        System.out.println("+----+----------------------+");

        for (Role role : roles){
            System.out.printf("| %-2d | %-20s |%n", role.getId(), role.getName());
        }
        System.out.println("+----+----------------------+");
    }

    public void showSuccess(String message){
        System.out.println(message);
    }
    public void showError(String message){
        System.out.println(message);
    }
}
