package org.repository;

import org.models.TaskComment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskCommentRepository {

    private final Connection connection;

    public TaskCommentRepository(Connection connection) {
        this.connection = connection;
    }

    public TaskComment save(TaskComment comment) throws SQLException {
        String sql = """
                INSERT INTO task_comment
                (title, comment, comment_date, id_task, id_user_workspace)
                VALUES (?, ?, CURDATE(), ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, comment.getTitle());
            ps.setString(2, comment.getComment());
            ps.setInt(3, comment.getIdTask());
            ps.setInt(4, comment.getIdUserWorkspace());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    comment.setIdComment(keys.getInt(1));
                }
            }
        }
        return comment;
    }

    public Optional<TaskComment> findById(int id) throws SQLException {
        String sql = "SELECT * FROM task_comment WHERE id_comment = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public List<TaskComment> findByTask(int idTask) throws SQLException {
        String sql = "SELECT * FROM task_comment WHERE id_task = ?";
        List<TaskComment> comments = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTask);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) comments.add(map(rs));
            }
        }
        return comments;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM task_comment WHERE id_comment = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private TaskComment map(ResultSet rs) throws SQLException {
        Date date = rs.getDate("comment_date");
        return new TaskComment(
                rs.getInt("id_comment"),
                rs.getString("title"),
                rs.getString("comment"),
                date,
                rs.getInt("id_task"),
                rs.getInt("id_user_workspace")
        );
    }
}
