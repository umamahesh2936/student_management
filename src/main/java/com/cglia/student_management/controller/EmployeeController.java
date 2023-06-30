package com.cglia.student_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cglia.student_management.dto.Employee;
import com.cglia.student_management.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private final RestTemplate restTemplate;
	String url = "http://192.168.60.55:7000/";
	@Autowired
	EmployeeService service;

	@Autowired
	public EmployeeController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("getbyid/{id}")
	public Employee getEmployeeData(@PathVariable String id) {
		String k = url;
		url = url + "getone/" + id;
		// Send HTTP GET request to the external web service
		String response = restTemplate.getForObject(url, String.class);
		// Process the response as needed
		url = k;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee employee = objectMapper.readValue(response, Employee.class);
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping("/save")
	public String postEmployeeData(@RequestBody Employee employee) {
		String k = url;
		url = url + "save";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create the request entity with the requestData as the body and headers
		HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);

		// Send HTTP POST request to the external web service
		String response = restTemplate.postForObject(url, requestEntity, String.class);
		url = k;
		// Process the response as needed
		return response;

	}

	@DeleteMapping("{id}")
	public String deleteEmployeeData(@PathVariable String id) {
		String k = url;
		url = url + "delete/" + id;
		// Send HTTP GET request to the external web service
		restTemplate.delete(url);
		// Process the response as needed
		url = k;
		return "deleted successfully";
	}

	@PutMapping("update/{id}")
	public String updateEmployeeData(@RequestBody Employee employee, @PathVariable String id) {
		String k = url;
		url = url + "/update/" + id;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create the request entity with the requestData as the body and headers
		HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);

		// Send HTTP POST request to the external web service
		restTemplate.put(url, requestEntity, id);
		url = k;
		// Process the response as needed
		return "updated successfully";
	}

	@GetMapping("getbyidclient/{id}")
	public Employee getEmployeeById(@PathVariable int id) {
		return service.getById(id);
	}

	@GetMapping("/getallclient")
	public List<Employee> getAllEmployees() {
		return service.getAllEmployee();
	}

	@PostMapping("/saveclient")
	public String saveEmp(@RequestBody Employee employee) {
		return service.saveEmployee(employee);
	}

	@DeleteMapping("/deleteclient/{id}")
	public String deleteEmp(@PathVariable int id) {
		return service.deleteEmployee(id);
	}

	@PutMapping("/updateclient/{id}")
	public Employee updateEmp(@RequestBody Employee employee, @PathVariable int id) {
		return service.updateEmployee(employee, id);
	}
}
