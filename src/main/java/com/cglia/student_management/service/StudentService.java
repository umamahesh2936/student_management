package com.cglia.student_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cglia.student_management.controller.StudentPrinciple;
import com.cglia.student_management.dao.StudentDao;
import com.cglia.student_management.dto.Student;

@Service
public class StudentService implements UserDetailsService {
	@Autowired
	private StudentDao studentDao;

	public Student createStudent(Student student) {
		return studentDao.createStudent(student);
	}

	public List<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	public Student getStudentById(int id) {
		return studentDao.getStudentById(id);
	}

	public Student updateStudent(Student student, int id) {
		return studentDao.updateStudent(student, id);
	}

	public String deleteStudent(int id) {
		return studentDao.deleteStudent(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentDao.getStudentByName(username);
		if (student == null) {
			throw new UsernameNotFoundException("STUDENT 404");
		}
		return new StudentPrinciple(student);
	}
}
