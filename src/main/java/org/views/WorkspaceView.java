package org.views;

import org.models.User;
import org.models.Workspace;
import org.models.WorkspaceSummary;
import org.repository.UserRepository;
import org.repository.WorkspaceRepository;

import java.util.List;
import java.util.Scanner;

public class WorkspaceView {
    Scanner scanner = new Scanner(System.in);
    WorkspaceRepository repository;

    public WorkspaceView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String askName() {
        System.out.print("Nombre del workspace: ");
        return scanner.nextLine().trim();
    }

    public int askWorkspaceId(){
        System.out.print("Numero id del espacio de trabajo: ");
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public int askUserId(){
        System.out.print("Numero id del usuario: ");
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public String askNewName(){
        System.out.println("Nuevo nombre: ");
        return scanner.nextLine().trim();
    }

    public void showWorkspace(Workspace w){
        System.out.println("ID: " + w.getId() + "\n Nombre: " + w.getName());
    }

    public void showAllWorkspaces(List<Workspace> list){
        if(list.isEmpty()){
            System.out.println("No hay workspaces registrados");
        }else{
            System.out.println("-------------Workspaces-------------");
            for(Workspace w: list){
                System.out.println("ID: " + w.getId() + "\n Nombre: " + w.getName());
            }
        }
    }

    public void showAllMembers(List<User> list){
        if(list.isEmpty()){
            System.out.println("No hay miembros registrados");
        }else{
            System.out.println("-------------miembros-------------");
            for(User u: list){
                System.out.println("ID: " + u.getId() + "\n Nombre: " + u.getName() + "\n Email: " + u.getMail());
            }
        }
    }

    public void showSummary(List<WorkspaceSummary> list) {
        if (list.isEmpty()) {
            System.out.println("No hay workspaces registrados.");
            return;
        }
        System.out.println("\n  RESUMEN DE WORKSPACES");
        System.out.printf("  %-20s %6s %9s %11s %10s %10s%n",
                "Workspace", "Total", "Pending", "InProgress", "Completed", "Cancelled");
        System.out.println("  " + "─".repeat(70));
        for (WorkspaceSummary ws : list) {
            System.out.printf("  %-20s %6d %9d %11d %10d %10d%n",
                    ws.getWorkspaceName(), ws.getTotalTasks(),
                    ws.getPending(), ws.getInProgress(),
                    ws.getCompleted(), ws.getCancelled());
        }
        System.out.println("  " + "─".repeat(70));
    }
}
