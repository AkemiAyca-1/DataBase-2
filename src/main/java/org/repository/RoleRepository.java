package org.repository;

import org.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    private final Connection connection;

    public RoleRepository(Connection connection) {
        this.connection = connection;
    }

    public Role saveRole(Role role) {
        String query = "insert into user_roles (name) values(?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());
            preparedStatement.executeUpdate();
//            try(ResultSet keys = preparedStatement.getGeneratedKeys()) {
//                if (keys.next()) {
//                    role.setId(keys.getInt(1));
//                    return role;
//                }
//            }
            System.out.println("Role asignado correctamente");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteRole(int id) throws SQLException {
        String query = "delete from user_roles where id_role = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        boolean anwere = preparedStatement.execute();
        if (anwere) return true;
        else return false;
    }

    public List<Role> findRoles() throws SQLException {
        String query = "select * from user_roles;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Role> roles = new ArrayList<>();
        while (resultSet.next()) {
            int id =  resultSet.getInt("id_role");
            String name = resultSet.getString("name");
            Role role = new Role(name);
            role.setId(id);
            roles.add(role);
        }
        return roles;
    }

    public void assignRole(int id_role, int id_user) throws SQLException {
        String query = "insert into roles_and_users (id_role, id_user) values (?, ?); ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_role);
        preparedStatement.setInt(2, id_user);
        preparedStatement.executeUpdate();
    }

    public void updateRole(int id, String name) throws SQLException {
        String query = "update user_roles set name = ? where id_role = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }
}
