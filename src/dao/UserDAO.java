package dao;

import model.Skill;
import model.User;

import java.sql.*;
import java.util.*;

public class UserDAO {

    public void insertUser(User u) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, location, bio) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getLocation());
            ps.setString(5, u.getBio());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int userId = keys.getInt(1);
                u.setId(userId);

                if (u.getSkills() != null && !u.getSkills().isEmpty()) {
                    String sqlSkill = "INSERT INTO user_skills (user_id, skill_id, level) VALUES (?,?,?)";
                    try (PreparedStatement psSkill = conn.prepareStatement(sqlSkill)) {
                        for (Skill s : u.getSkills()) {
                            psSkill.setInt(1, userId);
                            psSkill.setInt(2, s.getId());
                            psSkill.setString(3, "basic");
                            psSkill.addBatch();
                        }
                        psSkill.executeBatch();
                    }
                }
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT u.id, u.name, u.email, u.location, u.bio, " +
                "s.id AS skill_id, s.name AS skill_name, s.category " +
                "FROM users u " +
                "LEFT JOIN user_skills us ON u.id = us.user_id " +
                "LEFT JOIN skills s ON us.skill_id = s.id " +
                "ORDER BY u.id DESC";

        Map<Integer, User> map = new LinkedHashMap<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int userId = rs.getInt("id");
                User u = map.get(userId);
                if (u == null) {
                    u = new User();
                    u.setId(userId);
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setLocation(rs.getString("location"));
                    u.setBio(rs.getString("bio"));
                    u.setSkills(new ArrayList<>());
                    map.put(userId, u);
                }
                int skillId = rs.getInt("skill_id");
                if (skillId > 0) {
                    Skill s = new Skill();
                    s.setId(skillId);
                    s.setName(rs.getString("skill_name"));
                    s.setCategory(rs.getString("category"));
                    u.getSkills().add(s);
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<User> search(String skillFilter) throws SQLException {
        String sql = "SELECT u.id, u.name, u.email, u.location, u.bio, " +
                "s.id AS skill_id, s.name AS skill_name, s.category " +
                "FROM users u " +
                "LEFT JOIN user_skills us ON u.id = us.user_id " +
                "LEFT JOIN skills s ON us.skill_id = s.id " +
                "WHERE (? = '' OR s.name = ?) " +
                "ORDER BY u.id DESC";

        Map<Integer, User> map = new LinkedHashMap<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, skillFilter == null ? "" : skillFilter);
            ps.setString(2, skillFilter == null ? "" : skillFilter);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("id");
                    User u = map.get(userId);
                    if (u == null) {
                        u = new User();
                        u.setId(userId);
                        u.setName(rs.getString("name"));
                        u.setEmail(rs.getString("email"));
                        u.setLocation(rs.getString("location"));
                        u.setBio(rs.getString("bio"));
                        u.setSkills(new ArrayList<>());
                        map.put(userId, u);
                    }
                    int skillId = rs.getInt("skill_id");
                    if (skillId > 0) {
                        Skill s = new Skill(
                                rs.getInt("skill_id"),
                                rs.getString("skill_name"),
                                rs.getString("category")
                        );
                        u.getSkills().add(s);
                    }
                }
            }
        }
        return new ArrayList<>(map.values());
    }
}
