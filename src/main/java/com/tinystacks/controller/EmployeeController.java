package com.tinystacks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinystacks.exception.ResourceNotFoundException;
import com.tinystacks.model.Employee;
import com.tinystacks.model.LocalItem;
import com.tinystacks.repository.EmployeeRepository;




@RestController
@RequestMapping("/")
public class EmployeeController {

	ArrayList<LocalItem> items = new ArrayList<LocalItem>();

	@Autowired
	private EmployeeRepository employeeRepository;

	//Get one local item
	@GetMapping("/ping")
	public String getPing(){
		return "ok";
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Integer employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Integer employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	//Get All Local Items
	@GetMapping("/local-items")
	public ArrayList<LocalItem> getAllItems() {
		return items;
	}

	//Get one local item
	@GetMapping("/local-items/{id}")
	public LocalItem getOneItem(@PathVariable(value = "id") Integer localItemId){
		return items.get(localItemId-1);
	}

	//Craete one local item
	@PostMapping("/local-items")
	public LocalItem createOneItem(@RequestBody LocalItem itemDetails) {
		LocalItem itemNew = new LocalItem(itemDetails.getId(), itemDetails.getTitle(), itemDetails.getContent());
		items.add(itemNew);
		return itemNew;
	}

	//Update one local item
	@PutMapping("/local-items/{id}")
	public LocalItem updateOneItem(@RequestBody LocalItem itemDetails) {
		LocalItem itemNew = new LocalItem(itemDetails.getId(), itemDetails.getTitle(), itemDetails.getContent());
		items.add(itemNew);
		return itemNew;
	}

	//Delete one local item
	@DeleteMapping("/local-items/{id}")
	public ArrayList<LocalItem> deleteOneItem(@PathVariable(value = "id") Integer localItemId){
		items.remove(0);
		return items;
	}
}
