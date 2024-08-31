package br.com.jvrss.library.employee.controller;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.service.EmployeeService;
import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing employees.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LoginService loginService;

    /**
     * Creates a new employee.
     *
     * @param employee the employee to create
     * @return the created employee
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        Login loginResponse = loginService.getLoginById(employee.getLogin().getId());
        if (loginResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        employee.setLogin(loginResponse);

        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }

    /**
     * Retrieves an employee by their CPF.
     *
     * @param cpf the CPF of the employee
     * @return the employee, or 404 if not found
     */
    @GetMapping("/{cpf}")
    public ResponseEntity<Employee> getEmployeeByCpf(@PathVariable String cpf) {
        Optional<Employee> employee = employeeService.getEmployeeByCpf(cpf);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing employee.
     *
     * @param cpf the CPF of the employee to update
     * @param employee the updated employee data
     * @return the updated employee, or 404 if not found
     */
    @PutMapping("/{cpf}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String cpf, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(cpf, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes an employee by their CPF.
     *
     * @param cpf the CPF of the employee to delete
     * @return 204 No Content
     */
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String cpf) {
        employeeService.deleteEmployee(cpf);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}