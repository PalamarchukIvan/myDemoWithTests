package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee getById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Employee repostById(Integer id, Employee employee) {
        var entity = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (entity.equals(employee)) {
            return entity;
        }
        entity.setName(employee.getName());
        entity.setEmail(employee.getEmail());
        entity.setCountry(employee.getCountry());
        entity.setIsPrivate(employee.getIsPrivate());
        entity.setGender(employee.getGender());
        entity.setAddresses(employee.getAddresses());
        return employeeRepository.save(entity);
    }

    @Override
    public Employee patchById(Integer id, Employee employee) {
        var entity = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (entity.equals(employee)) {
            return entity;
        }

        if (employee.getName() != null && !employee.getName().equals(entity.getName()))
            entity.setName(employee.getName());

        if (employee.getEmail() != null && !employee.getEmail().equals(entity.getEmail()))
            entity.setEmail(employee.getEmail());

        if (employee.getCountry() != null && !employee.getCountry().equals(entity.getCountry()))
            entity.setCountry(employee.getCountry());

        if (employee.getGender() != null && !employee.getGender().equals(entity.getGender()))
            entity.setGender(employee.getGender());

        if (employee.getAddresses() != null && !employee.getAddresses().equals(entity.getAddresses()))
            entity.setAddresses(employee.getAddresses());

        if (employee.getIsPrivate() != null && !employee.getIsPrivate().equals(entity.getIsPrivate()))
            entity.setIsPrivate(employee.getIsPrivate());

        return employeeRepository.save(entity);
    }

    @Override
    public void removeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        employeeRepository.delete(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
        employeeRepository.resetSequenceEmployee();
        employeeRepository.resetSequenceAddress();
    }

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.of(opt);
    }

    @Override
    public List<Employee> findEmployeeIfAddressPresent() {
        return employeeRepository.findEmployeeByPresentAddress();
    }

    public List<Employee> findEmployeeByPartOfTheName(String letters) {
        return employeeRepository.findEmployeeByPartOfTheName(letters);
    }

    public List<Employee> filterPrivateEmployees(List<Employee> employees) {
        return employees.stream()
                .peek(employee -> {
                    if (employee.getIsPrivate()) {
                        makeEmployeeDataPrivate(employee);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public void generateTestDatabase(int numberOfEntities) {
        for (int i = 0; i <= numberOfEntities; i++) {
            var lastEmployeeId = employeeRepository.findLastEmployeeId();
            var lastAddressId = employeeRepository.findLastAddressId();
            int idEmployee = (lastEmployeeId == null) ? 1 : lastEmployeeId + 1;
            long idAddress = (lastAddressId == null) ? 1 : lastAddressId + 1;
            employeeRepository.save(createRandomEntity(idEmployee, idAddress));
        }
    }

    private static Employee createRandomEntity(int idEmployee, long idAddress) {
        return new Employee(
                idEmployee,
                "TestName ".concat(Integer.toString(idEmployee)),
                "testCountry ".concat(Integer.toString(idEmployee)),
                "testmaint@mail.ru",
                (Math.random() * 4 < 2) ? null : Set.of(new Address(
                        idAddress,
                        true,
                        "someCountry ".concat(Long.toString(idAddress)),
                        "someCity ".concat(Long.toString(idAddress)),
                        "someStreet ".concat(Long.toString(idAddress))
                )),
                (Math.random() * 2 > 1) ? Gender.M : Gender.F,
                false
        );
    }

    private void makeEmployeeDataPrivate(Employee employee) {
        employee.setName("Is private");
        employee.setAddresses(null);
        employee.setCountry("Is private");
        employee.setEmail("Is private");
        employee.setGender(null);
    }
}
