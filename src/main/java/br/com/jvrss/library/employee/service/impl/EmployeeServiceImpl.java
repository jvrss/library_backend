// src/main/java/br/com/jvrss/library/employee/service/EmployeeServiceImpl.java
package br.com.jvrss.library.employee.service;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeByCpf(String cpf) {
        return employeeRepository.findById(cpf);
    }

    @Override
    public Employee updateEmployee(String cpf, Employee employee) {
        if (employeeRepository.existsById(cpf)) {
            employee.setCpf(cpf);
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    @Override
    public void deleteEmployee(String cpf) {
        employeeRepository.deleteById(cpf);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}