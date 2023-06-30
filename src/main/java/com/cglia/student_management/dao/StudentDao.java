package com.cglia.student_management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.cglia.student_management.dto.Student;

@Repository
public class StudentDao {
	Connection connection;
	PreparedStatement statement;
	Statement statement2;
	@Autowired
	Student student;

	public Student createStudent(Student s) {

		try {
			LocalDateTime d = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String fd = d.format(formatter);
			connection = DataBaseConnection.getConnection();
			if (connection != null) {
				statement = connection.prepareStatement("INSERT INTO STUDENT VALUES (?, ?,?,?,?, ?, ?,?,?,?)");
				statement.setInt(1, 0);
				statement.setString(2, s.getName());
				statement.setInt(3, s.getAge());
				statement.setString(4, s.getBranch());
				statement.setString(5, fd + "");
				statement.setString(6, fd + "");
				statement.setString(7, s.getCreatedBy());
				statement.setString(8, s.getCreatedBy());
				statement.setInt(9, 1);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String encryptedPassword = passwordEncoder.encode(s.getPassword());
				statement.setString(10, encryptedPassword);
				int vid = statement.executeUpdate();
				if (vid > 0) {
					return s;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Student> getAllStudents() {

		List<Student> list = new ArrayList<>();
		try {
			connection = DataBaseConnection.getConnection();
			statement2 = connection.createStatement();
			ResultSet resultSet = statement2.executeQuery("SELECT * FROM STUDENT");
			while (resultSet.next()) {
				Student student1 = new Student();
				student1.setId(resultSet.getInt(1));
				student1.setName(resultSet.getString(2));
				student1.setAge(resultSet.getInt(3));
				student1.setBranch(resultSet.getString(4));
				student1.setCreatedOn(resultSet.getString(5));
				student1.setModifiedOn(resultSet.getString(6));
				student1.setCreatedBy(resultSet.getString(7));
				student1.setModifiedBy(resultSet.getString(8));
				student1.setStatus(resultSet.getInt(9));
				student1.setPassword(resultSet.getString(10));
				if (student1.getStatus() > 0) {
					list.add(student1);
				}
			}
			return list;
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();

	}

	public Student getStudentById(int id) {
		try {
			connection = DataBaseConnection.getConnection();
			statement = connection.prepareStatement("SELECT * FROM STUDENT WHERE STUDENT_ID=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				student.setId(resultSet.getInt(1));
				student.setName(resultSet.getString(2));
				student.setAge(resultSet.getInt(3));
				student.setBranch(resultSet.getString(4));
				student.setCreatedOn(resultSet.getString(5));
				student.setModifiedOn(resultSet.getString(6));
				student.setCreatedBy(resultSet.getString(7));
				student.setModifiedBy(resultSet.getString(8));
				student.setStatus(resultSet.getInt(9));
				student.setPassword(resultSet.getString(10));
			}
			if (student.getStatus() > 0) {
				return student;
			}
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Student updateStudent(Student stu, int id) {

		try {
			LocalDateTime d = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String fd = d.format(formatter);
			connection = DataBaseConnection.getConnection();
			statement = connection.prepareStatement(
					"UPDATE STUDENT SET STUDENT_NAME=?,STUDENT_AGE=?, STUDENT_BRANCH=?, STUDENT_MODIFIEDON=?,STUDENT_MODIFIEDBY=? WHERE STUDENT_ID=?");
			statement.setString(1, stu.getName());
			statement.setInt(2, stu.getAge());
			statement.setString(3, stu.getBranch());
			statement.setString(4, fd);
			statement.setString(5, stu.getModifiedBy());
			statement.setInt(6, id);
			int verify = statement.executeUpdate();
			if (verify > 0) {
				return stu;
			}
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String deleteStudent(int id) {

		try {
			connection = DataBaseConnection.getConnection();
			statement = connection.prepareStatement("SELECT * FROM STUDENT WHERE STUDENT_ID=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next() && (resultSet.getInt(9) == 1)) {
				statement = connection.prepareStatement("UPDATE STUDENT SET STUDENT_STATUS=? WHERE STUDENT_ID=?");
				statement.setInt(1, 0);
				statement.setInt(2, id);
				int verify = statement.executeUpdate();
				if (verify > 0) {
					return "Deleted Successfully";
				}
			}
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Student getStudentByName(String name) {
		try {
			connection = DataBaseConnection.getConnection();
			statement = connection.prepareStatement("SELECT * FROM STUDENT WHERE STUDENT_NAME=?");
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				student.setId(resultSet.getInt(1));
				student.setName(resultSet.getString(2));
				student.setAge(resultSet.getInt(3));
				student.setBranch(resultSet.getString(4));
				student.setCreatedOn(resultSet.getString(5));
				student.setModifiedOn(resultSet.getString(6));
				student.setCreatedBy(resultSet.getString(7));
				student.setModifiedBy(resultSet.getString(8));
				student.setStatus(resultSet.getInt(9));
				student.setPassword(resultSet.getString(10));
			}
			if (student.getStatus() > 0) {
				return student;
			}
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}