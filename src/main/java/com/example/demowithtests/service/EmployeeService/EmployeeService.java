package com.example.demowithtests.service.EmployeeService;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Employee create(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee repostById(Integer id, Employee plane);

    Employee patchById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    List<Employee> findEmployeeIfAddressPresent() throws NoSuchMethodException;
    List<Employee> findEmployeeByPartOfTheName(String letters);
    void generateTestDatabase(int numberOfEntities);
    List<Employee> findEmployeesWithExpiredPhotos();
    List<Employee> updateEmployeesWithExpiredPhotos();
    Employee addBadge(Integer idEmployee, Integer idBadge);
    Employee addBadge(Integer idEmployee, Badge Badge);
    Employee updateBadge(Integer idEmployee, Badge.State reason);
}
