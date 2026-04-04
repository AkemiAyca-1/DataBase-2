package org.repository;

import org.models.Task;
import org.enums.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {

    private final Connection connection;

    public TaskRepository(Connection connection) {
        this.connection = connection;
    }

    public Task save(Task task) throws SQLException {
        String sql = """
                INSERT INTO task (title, description, created_at, status, id_user_workspace, id_category)
                VALUES (?, ?, CURDATE(), ?, ?, ?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus().getDbValue());
            ps.setInt(4, task.getIdUserWorkspace());
            ps.setInt(5, task.getIdCategory());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) task.setIdTask(keys.getInt(1));
            }
        }
        return task;
    }

    public Optional<Task> findById(int idTask) throws SQLException {
        String sql = "SELECT * FROM task WHERE id_task = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTask);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public List<Task> findAll() throws SQLException {
        String sql = "SELECT * FROM task ORDER BY id_task";
        List<Task> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Task> findByCategory(int idCategory) throws SQLException {
        String sql = "SELECT * FROM task WHERE id_category = ? ORDER BY id_task";
        List<Task> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCategory);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<Task> findByUserWorkspace(int idUserWorkspace) throws SQLException {
        String sql = "SELECT * FROM task WHERE id_user_workspace = ? ORDER BY id_task";
        List<Task> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUserWorkspace);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean update(Task task) throws SQLException {
        String sql = """
                UPDATE task
                SET title = ?, description = ?, status = ?, id_user_workspace = ?, id_category = ?
                WHERE id_task = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus().getDbValue());
            ps.setInt(4, task.getIdUserWorkspace());
            ps.setInt(5, task.getIdCategory());
            ps.setInt(6, task.getIdTask());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idTask) throws SQLException {
        String sql = "DELETE FROM task WHERE id_task = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTask);
            return ps.executeUpdate() > 0;
        }
    }

    private Task map(ResultSet rs) throws SQLException {
        java.sql.Date sqlDate = rs.getDate("created_at");
        return new Task(
                rs.getInt("id_task"),
                rs.getString("title"),
                rs.getString("description"),
                sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null,
                Status.fromDbValue(rs.getString("status")),
                rs.getInt("id_user_workspace"),
                rs.getInt("id_category")
        );
    }
}