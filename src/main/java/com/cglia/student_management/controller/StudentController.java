package com.cglia.student_management.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.cglia.student_management.dto.Student;
import com.cglia.student_management.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	StudentService service;

	@GetMapping("/")
	public String login() {
		return "Login Success";
	}

	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping("/student")
	public String saveStudent(@RequestBody Student student) {
		Student student2 = service.createStudent(student);
		if (student2 != null) {
			return "Saved Successfully";
		} else {
			return "Incorrect Data";
		}
	}
	@PreAuthorize("hasRole('STUDENT')")
	@PutMapping("/student/{id}")
	public String updateStudent(@RequestBody Student student, @PathVariable int id) {
		Student student2 = service.updateStudent(student, id);
		if (student2 != null) {
			return "Updated Successfully";
		} else {
			return "Please Check The Details Provided";
		}
	}

	@PreAuthorize("hasRole('STUDENT')")
	@DeleteMapping("/student/{id}")
	public String delete(@PathVariable int id) {
		String s = service.deleteStudent(id);
		if (s != null) {
			return "Deleted Successfully";
		} else {
			return "Invalid Id";
		}
	}

	@GetMapping("/student")
	public List<Student> getStudents() {
		List<Student> list = service.getAllStudents();
		if (!list.isEmpty()) {
			return list;
		} else {
			return Collections.emptyList();
		}
	}

	@GetMapping("/student/{id}")
	public Student getStudentById(@PathVariable int id) {
		Student student = service.getStudentById(id);
		if (student != null) {
			return student;
		} else {
			return null;
		}
	}

}
