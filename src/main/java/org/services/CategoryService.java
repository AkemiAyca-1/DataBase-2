package org.services;

import org.entities.*;

public class CategoryService {
    public void create(Category category) { System.out.println("Executing: Create Category"); }
    public void read(int id) { System.out.println("Executing: Read Category " + id); }
    public void update(Category category) { System.out.println("Executing: Update Category"); }
    public void delete(int id) { System.out.println("Executing: Delete Category " + id); }
    public void listAll() { System.out.println("Executing: List All Categories"); }
}
