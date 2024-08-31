package br.com.jvrss.library.employee.service.impl;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.repository.EmployeeRepository;
import br.com.jvrss.library.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the EmployeeService interface.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a new employee.
     *
     * @param employee the employee to create
     * @return the created employee
     */
    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves an employee by their CPF.
     *
     * @param cpf the CPF of the employee
     * @return an Optional containing the employee if found, or an empty Optional if not found
     */
    @Override
    public Optional<Employee> getEmployeeByCpf(String cpf) {
        return employeeRepository.findById(cpf);
    }

    /**
     * Updates an existing employee.
     *
     * @param cpf the CPF of the employee to update
     * @param employee the updated employee data
     * @return the updated employee
     * @throws RuntimeException if the employee is not found
     */
    @Override
    public Employee updateEmployee(String cpf, Employee employee) {
        if (employeeRepository.existsById(cpf)) {
            employee.setCpf(cpf);
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    /**
     * Deletes an employee by their CPF.
     *
     * @param cpf the CPF of the employee to delete
     */
    @Override
    public void deleteEmployee(String cpf) {
        employeeRepository.deleteById(cpf);
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}