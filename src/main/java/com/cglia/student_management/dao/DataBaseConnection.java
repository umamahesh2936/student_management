package com.cglia.student_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DataBaseConnection {
	static Connection connection;

	DataBaseConnection() {
	}

	public static Connection getConnection() {
		try {
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://192.168.60.30:3306/umamahesh", "umamahesh",
						"uma@12345");
				return connection;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
