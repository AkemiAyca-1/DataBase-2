package org.repository;

import org.models.AdminUser;
import org.models.RegularUser;
import org.models.User;
import org.utils.*;

import java.nio.file.FileAlreadyExistsException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Connection connection;


    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User save(User user) throws SQLException {
        String query = "insert into `user` (name, email, password) value (?,?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2,user.getMail());
        preparedStatement.setString(3, PasswordHasher.hashPassword(user.getPassword()));
        System.out.println("HASH REGISTRO: " + PasswordHasher.hashPassword(user.getPassword()));
        preparedStatement.executeUpdate();
        try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
            if (keys.next()) user.setId(keys.getInt(1));
        }
        return user;
    }

    public boolean update(User update) throws SQLException {
        String query = "update `user` set name = ?, password = ?, email = ?  where id_user = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, update.getName());
        preparedStatement.setString(2, PasswordHasher.hashPassword(update.getPassword()));
        preparedStatement.setString(3, update.getMail());
        preparedStatement.setInt(4, update.getId());
        return preparedStatement.executeUpdate() > 0;
    }


    public boolean delete(int id_user) throws SQLException {
        String query = "delete from `user` where id_user = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_user);
        boolean answer = preparedStatement.execute();
        if (answer) {
            return true;
        }
        return false;
    }

    public List<User> findAll() throws SQLException {
        String query = "select * from `user`;";
        List<User> users = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id_user");
            String name = resultSet.getString("name");
            String mail = resultSet.getString("email");
            String password = resultSet.getString("password");
            User user = new RegularUser(name, mail, password);
            user.setId(id);
            users.add(user);
        }
        return users;
    }

    public User getUser(int id){
        String query = "select * from `user` where id_user = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String mail = resultSet.getString("email");
                String password = resultSet.getString("password");
                var user = new RegularUser(name, mail, password);
                user.setId(id);
                return user;
            }
        }catch (Exception e){
            System.out.println("Usuario no encontrado");
        }
        return null;
    }

    public User getUserByName(String name, String password ) {
        String query = "select * from `user` where name = ? and  password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id_user = resultSet.getInt("id_user");
                String nameU = resultSet.getString("name");
                String mail = resultSet.getString("email");
                String passwordU = resultSet.getString("password");
                return new AdminUser(id_user, nameU, mail, passwordU);
            }
        }catch (Exception e){
            System.out.println("Usuario no encontrado");
        }
        return null;
    }

    public List<Map<String, String>> getUsersWithRoles() {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM usuariosConRoles";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, String> registro = new HashMap<>();
                registro.put("nombre", rs.getString("nombreUsuario"));
                registro.put("email", rs.getString("email"));
                registro.put("rol", rs.getString("RolAsociado"));
                list.add(registro);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public String getOneUserWithRole(int id) {
        String query = "select * from usuariosConRoles where id_user = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("RolAsociado");
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return null;
    }
}
