package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    // CREATE (insert user)
    public void insertUser(User u) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO users (name, email, password, bio, location, role) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, u.getName());
        stmt.setString(2, u.getEmail());
        stmt.setString(3, u.getPassword());
        stmt.setString(4, u.getBio());
        stmt.setString(5, u.getLocation());
        stmt.setString(6, u.getRole());

        stmt.executeUpdate();
        conn.close();
    }

    // READ (all users)
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> lista = new ArrayList<>();
        Connection conn = DBConnection.getConnection();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            User u = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("bio"),
                    rs.getString("location"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                    rs.getTimestamp("last_login") != null ? rs.getTimestamp("last_login").toLocalDateTime() : null
            );
            lista.add(u);
        }

        conn.close();
        return lista;
    }

    // READ (find user by ID)
    public User getUserById(int id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        User u = null;

        if (rs.next()) {
            u = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("bio"),
                    rs.getString("location"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                    rs.getTimestamp("last_login") != null ? rs.getTimestamp("last_login").toLocalDateTime() : null
            );
        }

        conn.close();
        return u;
    }

    // UPDATE
    public void updateUser(User u) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "UPDATE users SET name=?, email=?, password=?, bio=?, location=?, role=?, last_login=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, u.getName());
        stmt.setString(2, u.getEmail());
        stmt.setString(3, u.getPassword());
        stmt.setString(4, u.getBio());
        stmt.setString(5, u.getLocation());
        stmt.setString(6, u.getRole());
        stmt.setTimestamp(7, u.getLastLogin() != null ? Timestamp.valueOf(u.getLastLogin()) : null);
        stmt.setInt(8, u.getId());

        stmt.executeUpdate();
        conn.close();
    }

    // DELETE
    public void deleteUserById(int id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        conn.close();
    }
}
