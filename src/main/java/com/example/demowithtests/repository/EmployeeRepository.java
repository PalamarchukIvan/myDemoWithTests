package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
//@Component
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

<<<<<<< HEAD
    @Query(value = "select users.* from users inner join addresses on addresses.employee_id = users.id", nativeQuery = true)
    List<Employee> findEmployeeByPresentAddress();
=======
    @Query(value = "select e from Employee e where lower(e.name) like concat('%', lower(:letters), '%') ")
    List<Employee> findEmployeeByPartOfTheName(String letters);
>>>>>>> jpql
}
