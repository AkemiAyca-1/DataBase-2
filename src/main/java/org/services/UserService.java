package org.services;

import org.entities.*;
import org.abstracts.User;

public class UserService {
    public void create(User user) { System.out.println("Executing: Create User"); }
    public void read(int id) { System.out.println("Executing: Read User " + id); }
    public void update(User user) { System.out.println("Executing: Update User"); }
    public void delete(int id) { System.out.println("Executing: Delete User " + id); }
    public void listAll() { System.out.println("Executing: List All Users"); }
}
