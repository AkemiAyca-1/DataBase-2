package org.entities;

public class Category {
    private int idCategory;
    private String name;
    private int idUser;

    public Category() {}

    public Category(String name, int idUser) {
        this.name   = name;
        this.idUser = idUser;
    }

    public Category(int idCategory, String name, int idUser) {
        this.idCategory = idCategory;
        this.name       = name;
        this.idUser     = idUser;
    }

    public int getIdCategory()             { return idCategory; }
    public void setIdCategory(int id)      { this.idCategory = id; }

    public String getName()                { return name; }
    public void setName(String name)       { this.name = name; }

    public int getIdUser()                 { return idUser; }
    public void setIdUser(int idUser)      { this.idUser = idUser; }

    @Override
    public String toString() {
        return String.format("[%d] %-30s (user_id: %d)", idCategory, name, idUser);
    }
}