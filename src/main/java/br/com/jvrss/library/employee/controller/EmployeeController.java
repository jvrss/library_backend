// src/main/java/br/com/jvrss/library/employee/controller/EmployeeController.java
package br.com.jvrss.library.employee.controller;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Employee> getEmployeeByCpf(@PathVariable String cpf) {
        Optional<Employee> employee = employeeService.getEmployeeByCpf(cpf);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String cpf, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(cpf, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String cpf) {
        employeeService.deleteEmployee(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}