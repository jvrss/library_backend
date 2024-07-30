// src/main/java/br/com/jvrss/library/employee/service/EmployeeService.java
package br.com.jvrss.library.employee.service;

import br.com.jvrss.library.employee.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Optional<Employee> getEmployeeByCpf(String cpf);
    Employee updateEmployee(String cpf, Employee employee);
    void deleteEmployee(String cpf);
    List<Employee> getAllEmployees();
}