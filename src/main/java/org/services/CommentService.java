package org.services;

import org.entities.*;

public class CommentService {
    public void create(Comment comment) { System.out.println("Executing: Create Comment"); }
    public void read(int id) { System.out.println("Executing: Read Comment " + id); }
    public void update(Comment comment) { System.out.println("Executing: Update Comment"); }
    public void delete(int id) { System.out.println("Executing: Delete Comment " + id); }
    public void listAll() { System.out.println("Executing: List All Comments"); }
}