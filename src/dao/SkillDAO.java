package dao;

import model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    public List<Skill> getAllSkills() throws SQLException {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT id, name, category FROM skills ORDER BY name";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Skill s = new Skill(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category")
                );
                list.add(s);
            }
        }
        return list;
    }
}
