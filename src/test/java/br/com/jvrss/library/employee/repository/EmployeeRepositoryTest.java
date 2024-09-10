package br.com.jvrss.library.employee.repository;

import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.login.model.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private Login login;

    @BeforeEach
    void setUp() {
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
    void testSaveEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getCpf()).isEqualTo(employee.getCpf());
    }

    @Test
    void testFindEmployeeByCpf() {
        employeeRepository.save(employee);
        Optional<Employee> foundEmployee = employeeRepository.findById(employee.getCpf());
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getCpf()).isEqualTo(employee.getCpf());
    }

    @Test
    void testUpdateEmployee() {
        employeeRepository.save(employee);
        employee.getLogin().setName("johnsmith");
        Employee updatedEmployee = employeeRepository.save(employee);
        assertThat(updatedEmployee.getLogin().getName()).isEqualTo("johnsmith");
    }

    @Test
    void testDeleteEmployee() {
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getCpf());
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getCpf());
        assertThat(deletedEmployee).isNotPresent();
    }
}