package com.dao;

import com.model.User;
import com.utility.DatabaseUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAOInterface {
    @Override
    public User getUserByUsername(String username) {
		return null;
    }

    @Override
    public void addUser(User user) {
        // ใช้ bcrypt ในการเข้ารหัสรหัสผ่าน
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, hashedPassword);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUsernameTaken(String username) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0; // ถ้ามีจำนวนมากกว่า 0 แสดงว่ามีชื่อผู้ใช้ซ้ำ
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // คืนค่า false หากเกิดข้อผิดพลาดในการเชื่อมต่อฐานข้อมูล
    }

}
