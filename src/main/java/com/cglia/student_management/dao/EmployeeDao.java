package com.cglia.student_management.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cglia.student_management.dto.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeDao {
	WebClient webClient;
	String url = "http://192.168.60.55:7000/";

//	@PostConstruct
//	public void init() {
//		webClient = WebClient.builder().baseUrl(url)
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//	}

	public Employee getEmployeeById(int id) {
		Mono<ResponseEntity<Employee>> responseMono = webClient.get().uri(url + "getone/" + id).retrieve()
				.toEntity(Employee.class);
		ResponseEntity<Employee> responseEntity = responseMono.block();
		Employee employee = responseEntity.getBody();
		return employee;
	}

	public List<Employee> getAllEmployees() {
		Flux<Employee> employeeFlux = webClient.get().uri("getall").retrieve().bodyToFlux(Employee.class);
		List<Employee> employeeList = employeeFlux.collectList().block();
		return employeeList;
	}

	public String saveEmp(Employee employee) {
		return webClient.post().uri("save").syncBody(employee).retrieve().bodyToMono(String.class).block();
	}

	public String deleteEmp(int id) {
		return webClient.delete().uri("delete/" + id).retrieve().bodyToMono(String.class).block();
	}

	public Employee updateEmp(Employee employee, int id) {
		Mono<Employee> mono = webClient.put().uri(url + "update/" + id).syncBody(employee).retrieve()
				.bodyToMono(Employee.class);
		return mono.block();

	}
}
