package com.cglia.student_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglia.student_management.dao.EmployeeDao;
import com.cglia.student_management.dto.Employee;

@Service
public class EmployeeService {
	@Autowired
	EmployeeDao dao;

	public Employee getById(int id) {
		return dao.getEmployeeById(id);
	}

	public List<Employee> getAllEmployee() {
		return dao.getAllEmployees();
	}

	public Employee updateEmployee(Employee emp, int id) {
		return dao.updateEmp(emp, id);
	}

	public String deleteEmployee(int id) {
		return dao.deleteEmp(id);
	}

	public String saveEmployee(Employee employee) {
		return dao.saveEmp(employee);
	}
}
