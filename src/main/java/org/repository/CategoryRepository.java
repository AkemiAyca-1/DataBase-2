package org.repository;

import org.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository {

    private final Connection connection;

    public CategoryRepository(Connection connection) {
        this.connection = connection;
    }

    public Category save(Category category) throws SQLException {
        String sql = "INSERT INTO category (name, id_user) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getIdUser());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    category.setIdCategory(keys.getInt(1));
                }
            }
        }
        return category;
    }

    public Optional<Category> findById(int idCategory) throws SQLException {
        String sql = "SELECT id_category, name, id_user FROM category WHERE id_category = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCategory);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public List<Category> findAll() throws SQLException {
        String sql = "SELECT id_category, name, id_user FROM category ORDER BY id_category";
        List<Category> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /** Todas las categorías que pertenecen a un usuario específico. */
    public List<Category> findByUser(int idUser) throws SQLException {
        String sql = "SELECT id_category, name, id_user FROM category WHERE id_user = ? ORDER BY id_category";
        List<Category> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }


    public boolean update(Category category) throws SQLException {
        String sql = "UPDATE category SET name = ?, id_user = ? WHERE id_category = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getIdUser());
            ps.setInt(3, category.getIdCategory());
            return ps.executeUpdate() > 0;
        }
    }


    public boolean delete(int idCategory) throws SQLException {
        String sql = "DELETE FROM category WHERE id_category = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCategory);
            return ps.executeUpdate() > 0;
        }
    }


    private Category map(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id_category"),
                rs.getString("name"),
                rs.getInt("id_user")
        );
    }
}