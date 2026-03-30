package org.services;

import org.entities.*;

public class TaskService {
    public void create(Task task) { System.out.println("Executing: Create Task"); }
    public void read(int id) { System.out.println("Executing: Read Task " + id); }
    public void update(Task task) { System.out.println("Executing: Update Task"); }
    public void delete(int id) { System.out.println("Executing: Delete Task " + id); }
    public void listAll() { System.out.println("Executing: List All Tasks"); }
}