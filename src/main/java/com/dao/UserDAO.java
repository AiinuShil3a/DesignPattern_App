package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.model.User;
import com.utility.DatabaseUtil;

public class UserDAO {
    // ในคลาสนี้เราจะสร้างเมดท็อดสำหรับการดึงข้อมูลผู้ใช้จากฐานข้อมูล
	public User getUserByUsername(String username) {
	    String query = "SELECT * FROM Users WHERE username = ?";
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String userUsername = resultSet.getString("username");
	            String password = resultSet.getString("password");
	            User user = new User(userUsername, password);
	            return user;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;  // หากไม่พบผู้ใช้
	}

}
