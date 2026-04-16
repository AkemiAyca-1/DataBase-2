package org.repository;

import org.models.RegularUser;
import org.models.User;
import org.models.Workspace;
import org.models.WorkspaceSummary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceRepository {
    private final Connection connection;

    public WorkspaceRepository(Connection connection) {
        this.connection = connection;
    }
    public boolean delete(int id) throws SQLException {
        String sql = "delete from workspace where id_workspace = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateName(int id, String newName)throws SQLException{
        String sql = "update workspace set name = ? where id_workspace = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Workspace saveWorkspace(Workspace workspace) throws SQLException {
        String sql = "insert into workspace (name) values (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, workspace.getName());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) workspace.setId(keys.getInt(1));
            }
        }
        return workspace;
    }

    private Workspace mapWorkspace(ResultSet rs) throws SQLException {
        return new Workspace(rs.getInt("id_workspace"), rs.getString("name"));
    }

    public List<Workspace> findAll() throws SQLException {
        String sql = "select id_workspace, name from workspace order by id_workspace";
        List<Workspace> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapWorkspace(rs));
        }
        return list;
    }

    public boolean addMember(int idWorkspace, int idUser) throws SQLException {
        String sql = "insert into user_workspace (id_workspace, id_user) values (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idWorkspace);
            ps.setInt(2, idUser);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteMember(int idWorkspace, int idUser) throws SQLException {
        String sql = "delete from user_workspace where id_workspace = ? and id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idWorkspace);
            ps.setInt(2, idUser);
            return ps.executeUpdate() > 0;
        }
    }
    public List<User> findMembersByWorkspace(int idWorkspace) throws SQLException {
        String sql = "select u.id_user, u.name, u.email, u.password from user u join user_workspace uw on u.id_user = uw.id_user where uw.id_workspace = ? order by u.id_user";
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idWorkspace);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegularUser u = new RegularUser(rs.getString("name"), rs.getString("email"), rs.getString("password"));
                    u.setId(rs.getInt("id_user"));
                    users.add(u);
                }
            }
        }
        return users;
    }
    public List<WorkspaceSummary> getSummary() throws SQLException {
        String sql = "SELECT * FROM workspace_summary";
        List<WorkspaceSummary> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new WorkspaceSummary(
                        rs.getString("workspace_name"),
                        rs.getInt("total_tasks"),
                        rs.getInt("pending"),
                        rs.getInt("in_progress"),
                        rs.getInt("completed"),
                        rs.getInt("cancelled")
                ));
            }
        }
        return list;
    }

    public boolean existsUserWorkspace(int id) throws SQLException {
        String sql = "SELECT 1 FROM user_workspace WHERE id_user_workspace = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsWorkspace(int id) throws SQLException {
        String sql = "SELECT 1 FROM workspace WHERE id_workspace = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
