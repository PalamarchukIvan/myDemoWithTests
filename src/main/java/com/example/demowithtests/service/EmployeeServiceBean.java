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
        return employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    entity.setIsPrivate(employee.getIsPrivate());
                    entity.setGender(employee.getGender());
                    entity.setAddresses(employee.getAddresses());
                    return employeeRepository.save(entity);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Employee patchById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName() == null ? entity.getName() : employee.getName());
                    entity.setEmail(employee.getEmail() == null ? entity.getEmail() : employee.getEmail());
                    entity.setCountry(employee.getCountry() == null ? entity.getCountry() : employee.getCountry());
                    entity.setGender(employee.getGender() == null ? entity.getGender() : employee.getGender());
                    entity.setAddresses(employee.getAddresses() == null ? entity.getAddresses() : employee.getAddresses());
                    entity.setIsPrivate(employee.getIsPrivate() == null ? entity.getIsPrivate() : employee.getIsPrivate());
                    return employeeRepository.save(entity);
                }).orElseThrow(ResourceNotFoundException::new);
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
        for (int i = 0; i < numberOfEntities; i++) {
            int id = employeeRepository.findLastEmployeeId() + 1;
            employeeRepository.save(new Employee(
                    id,
                    "TestName ".concat(Integer.toString(id)),
                    "testCountry".concat(Integer.toString(id)),
                    "testmaint@mail.ru",
                    (int) (Math.random() * 4) < 3 ? null : Set.of(new Address(
                            (long) (employeeRepository.findLastAddressId() + 1),
                            true,
                            "someCountry",
                            "someCity",
                            "someStreet"
                    )),
                    (int) (Math.random() * 2) > 1 ? Gender.M : Gender.F,
                    false
            ));
        }
    }

    private void makeEmployeeDataPrivate(Employee employee) {
        employee.setName("Is private");
        employee.setAddresses(null);
        employee.setCountry("Is private");
        employee.setEmail("Is private");
        employee.setGender(null);
    }
}
