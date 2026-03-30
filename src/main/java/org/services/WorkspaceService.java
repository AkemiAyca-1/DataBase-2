package org.services;

import org.entities.*;

public class WorkspaceService {
    public void create(Workspace workspace) { System.out.println("Executing: Create Workspace"); }
    public void read(int id) { System.out.println("Executing: Read Workspace " + id); }
    public void update(Workspace workspace) { System.out.println("Executing: Update Workspace"); }
    public void delete(int id) { System.out.println("Executing: Delete Workspace " + id); }
    public void listAll() { System.out.println("Executing: List All Workspaces"); }
}