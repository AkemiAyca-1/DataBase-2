package org.repository;

import org.models.RegularUser;
import org.models.User;

import java.nio.file.FileAlreadyExistsException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User save(User user) throws SQLException {
        String query = "insert into user (name, email, password) value (?,?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2,user.getMail());
        preparedStatement.setString(3,user.getPassword());
        preparedStatement.executeUpdate();
        try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
            if (keys.next()) user.setId(keys.getInt(1));
        }
        return user;
    }

    public boolean update(int id_user, String newMail) throws SQLException {
        String query = "update user set email = ? where id_user = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newMail);
        preparedStatement.setInt(2, id_user);
        boolean answer = preparedStatement.execute();
        if (answer) {
            return true;
        }
        return false;
    }

    public boolean delete(int id_user) throws SQLException {
        String query = "delete from user where id_user = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_user);
        boolean answer = preparedStatement.execute();
        if (answer) {
            return true;
        }
        return false;
    }

    public List<User> findAll() throws SQLException {
        String query = "select * from user;";
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
        String query = "select * from user where id_user = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String mail = resultSet.getString("email");
                String password = resultSet.getString("password");
                return new RegularUser(name, mail, password);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
