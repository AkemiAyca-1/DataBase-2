package org.controllers;

import org.models.Category;
import org.repository.CategoryRepository;
import org.views.CategoryView;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryView view;

    public CategoryController(CategoryRepository repository, CategoryView view) {
        this.repository = repository;
        this.view = view;
    }

    public void create() {
        Category category = view.askCategoryData();
        if (category == null) return;
        if (category.getName().isBlank()) {
            view.showError("El nombre no puede estar vacío.");
            return;
        }
        try {
            Category saved = repository.save(category);
            view.showSuccess("Categoría creada: " + saved);
        } catch (SQLException e) {
            view.showError("Error al crear la categoría: " + e.getMessage());
        }
    }

    public void readById() {
        int id = view.askId();
        try {
            Optional<Category> result = repository.findById(id);
            if (result.isPresent()) view.showCategory(result.get());
            else                    view.showError("No existe una categoría con id " + id);
        } catch (SQLException e) {
            view.showError("Error al buscar: " + e.getMessage());
        }
    }

    public void update() {
        int id = view.askId();
        try {
            Optional<Category> existing = repository.findById(id);
            if (existing.isEmpty()) {
                view.showError("No existe una categoría con id " + id);
                return;
            }
            view.showCategory(existing.get());
            Category updated = view.askCategoryData();
            if (updated == null) return;
            updated.setIdCategory(id);
            if (repository.update(updated)) view.showSuccess("Categoría actualizada: " + updated);
            else                            view.showError("No se pudo actualizar.");
        } catch (SQLException e) {
            view.showError("Error al actualizar: " + e.getMessage());
        }
    }

    public void delete() {
        int id = view.askId();
        if (!view.confirmDelete(id)) { view.showMessage("Cancelado."); return; }
        try {
            if (repository.delete(id)) view.showSuccess("Categoría " + id + " eliminada.");
            else                       view.showError("No existe una categoría con id " + id);
        } catch (SQLException e) {
            view.showError("Error al eliminar: " + e.getMessage());
        }
    }

    public void listAll() {
        try {
            List<Category> list = repository.findAll();
            view.showAll(list);
        } catch (SQLException e) {
            view.showError("Error al listar: " + e.getMessage());
        }
    }

    public void listByUser() {
        int idUser = view.askUserId();
        try {
            List<Category> list = repository.findByUser(idUser);
            view.showAll(list);
        } catch (SQLException e) {
            view.showError("Error al filtrar: " + e.getMessage());
        }
    }
}