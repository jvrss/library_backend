package br.com.jvrss.library.employee.service;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.repository.EmployeeRepository;
import br.com.jvrss.library.employee.service.impl.EmployeeServiceImpl;
import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LoginService loginService;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private Employee employee;
    private Login login;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        login = new Login();
        login.setId(UUID.randomUUID());
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");

        employee = new Employee();
        employee.setCpf("12345678900");
        employee.setLogin(login);
    }

    @Test
    void testCreateEmployee() {
        when(loginService.getLoginById(any(UUID.class))).thenReturn(login);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);
        assertThat(createdEmployee).isNotNull();
        assertThat(createdEmployee.getCpf()).isEqualTo(employee.getCpf());
    }

    @Test
    void testGetEmployeeByCpf() {
        when(employeeRepository.findById(any(String.class))).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeByCpf("12345678900");
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getCpf()).isEqualTo(employee.getCpf());
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee("12345678900", employee);
        assertThat(updatedEmployee.getLogin().getName()).isEqualTo("johndoe");
    }

    @Test
    void testDeleteEmployee() {
        employeeService.deleteEmployee("12345678900");
        Optional<Employee> deletedEmployee = employeeRepository.findById("12345678900");
        assertThat(deletedEmployee).isNotPresent();
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertThat(allEmployees).hasSize(1);
        assertThat(allEmployees.iterator().next().getCpf()).isEqualTo(employee.getCpf());
    }
}