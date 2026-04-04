package org.ui;

import org.entities.Category;

import java.util.List;
import java.util.Scanner;

public class CategoryView {

    private final Scanner scanner;

    public CategoryView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int askId() {
        System.out.print("  ID de la categoría: ");
        return readInt();
    }

    public int askUserId() {
        System.out.print("  ID del usuario: ");
        return readInt();
    }

    public Category askCategoryData() {
        System.out.print("  Nombre: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            showError("El nombre no puede estar vacío.");
            return null;
        }
        System.out.print("  ID del usuario propietario: ");
        int idUser = readInt();
        return new Category(name, idUser);
    }

    public boolean confirmDelete(int id) {
        System.out.printf("  ¿Eliminar categoría con id %d? (s/n): ", id);
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }

    public void showCategory(Category c) {
        System.out.printf("  ID      : %d%n", c.getIdCategory());
        System.out.printf("  Nombre  : %s%n", c.getName());
        System.out.printf("  Usuario : %d%n", c.getIdUser());
    }

    public void showAll(List<Category> list) {
        if (list.isEmpty()) { System.out.println("  (Sin registros)"); return; }
        list.forEach(c -> System.out.printf("  %s%n", c));
    }

    public void showSuccess(String msg) { System.out.println("Éxito: " + msg); }
    public void showError(String msg)   { System.out.println("Error: " + msg); }
    public void showMessage(String msg) { System.out.println("Info: " + msg); }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}