package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.anotations.InitMyAnnotations;
import com.example.demowithtests.util.anotations.Name;
import com.example.demowithtests.util.anotations.ShortenCountry;
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
    @InitMyAnnotations(annotations = {Name.class, ShortenCountry.class})
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
    @InitMyAnnotations(annotations = {Name.class})
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
        entity.setPhone(employee.getPhone());
        entity.setPassword(employee.getPassword());
        return employeeRepository.save(entity);
    }

    @Override
    @InitMyAnnotations(annotations = {Name.class})
    public Employee patchById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    if (entity.equals(employee)) {
                        return entity;
                    }
                    if (employee.getName() != null && !entity.getName().equals(employee.getName()))
                        entity.setName(employee.getName());

                    if (employee.getEmail() != null && !entity.getEmail().equals(employee.getEmail()))
                        entity.setEmail(employee.getEmail());

                    if (employee.getCountry() != null && !entity.getCountry().equals(employee.getCountry()))
                        entity.setCountry(employee.getCountry());

                    if (employee.getGender() != null && !entity.getGender().equals(employee.getGender()))
                        entity.setGender(employee.getGender());

                    if (employee.getAddresses() != null && !entity.getAddresses().equals(employee.getAddresses()))
                        entity.setAddresses(employee.getAddresses());

                    if (employee.getIsPrivate() != null && !entity.getIsPrivate().equals(employee.getIsPrivate()))
                        entity.setIsPrivate(employee.getIsPrivate());

                    if (employee.getPassword() != null && !entity.getPassword().equals(employee.getPassword()))
                        entity.setPassword(employee.getPassword());

                    if (employee.getPhone() != null && !entity.getPhone().equals(employee.getPhone()))
                        entity.setPhone(employee.getPhone());
                    return employeeRepository.save(entity);
                })
                .orElseThrow(ResourceNotFoundException::new);
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
    @Override
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
        List<Employee> list = new LinkedList<>();
        for (int i = 0; i < numberOfEntities; i++) {
            list.add(createRandomEntity(i));
        }
        employeeRepository.saveAll(list);
    }

    private static Employee createRandomEntity(int i) {
        return Employee.builder()
                .name("test name " + i)
                .country("test country " +i)
                .email("testmail@mail.ru")
                .phone("+380971362935")
                .addresses((Math.random() * 4 < 2) ? null : Set.of(Address.builder()
                        .addressHasActive(true)
                        .country("someCountry ".concat(Long.toString(i)))
                        .city("someCity ".concat(Long.toString(i)))
                        .street("someStreet ".concat(Long.toString(i)))
                        .build()
                ))
                .gender((Math.random() * 2 > 1) ? Gender.M : Gender.F)
                .isPrivate(false)
                .password("123321")
                .build();
    }

    private void makeEmployeeDataPrivate(Employee employee) {
        employee.setName("Is private");
        employee.setAddresses(null);
        employee.setCountry("Is private");
        employee.setEmail("Is private");
        employee.setGender(null);
        employee.setPassword("is private");
        employee.setPhone("is private");
    }
}
