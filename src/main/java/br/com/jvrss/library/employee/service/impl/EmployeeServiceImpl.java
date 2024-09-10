package br.com.jvrss.library.employee.service.impl;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.repository.EmployeeRepository;
import br.com.jvrss.library.employee.service.EmployeeService;
import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the EmployeeService interface.
 * Provides methods for managing employees.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LoginService loginService;

    /**
     * Creates a new employee.
     *
     * @param employee the employee to create
     * @return the created employee
     */
    @Override
    public Employee createEmployee(Employee employee) {
        Login login = loginService.getLoginById(employee.getLogin().getId());
        employee.setLogin(login);
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves an employee by their CPF.
     *
     * @param cpf the CPF of the employee
     * @return an Optional containing the found employee, or empty if not found
     */
    @Override
    public Optional<Employee> getEmployeeByCpf(String cpf) {
        return employeeRepository.findById(cpf);
    }

    /**
     * Updates an existing employee.
     *
     * @param cpf the CPF of the employee to update
     * @param employee the updated employee details
     * @return the updated employee
     */
    @Override
    public Employee updateEmployee(String cpf, Employee employee) {
        return employeeRepository.save(employee);
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