package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepositoryEM {
    List<Employee> findEmployeeByPartOfTheName(String letters);
    @Query(value = "select employees.* from employees inner join addresses on addresses.employee_id = employees.id", nativeQuery = true)
    List<Employee> findEmployeeByPresentAddress() throws NoSuchMethodException;
}
