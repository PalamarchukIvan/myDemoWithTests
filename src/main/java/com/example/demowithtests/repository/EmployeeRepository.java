package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);
    @NotNull
    Page<Employee> findAll(Pageable pageable);
    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    @Query(value = "select employees.* from employees inner join addresses on addresses.employee_id = employees.id", nativeQuery = true)
    List<Employee> findEmployeeByPresentAddress();
    @Query(value = "select e from Employee e where lower(e.name) like concat('%', lower(:letters), '%') ")
    List<Employee> findEmployeeByPartOfTheName(String letters);
    @Query(value = "select max(employees.id) from employees", nativeQuery = true)
    Integer findLastEmployeeId();
    @Query(value = "select max(addresses.id) from addresses", nativeQuery = true)
    Integer findLastAddressId();

    @Query(value = "SELECT setval('employees_id_seq', 1)", nativeQuery = true)
    void resetSequenceEmployee();
    @Query(value = "SELECT setval('addresses_id_seq', 1)", nativeQuery = true)
    void resetSequenceAddress();
}
