// src/main/java/br/com/jvrss/library/employee/repository/EmployeeRepository.java
package br.com.jvrss.library.employee.repository;

import br.com.jvrss.library.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}