package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.EmployeeRepositoryEMBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests {
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeRepositoryEMBean employeeRepositoryEMBean;

    private static class Samples {
        static Employee employee1 = Employee.builder()
                .id(1)
                .name("test name")
                .build();
        static Employee employee2 = Employee.builder()
                .id(3)
                .name("test name1")
                .build();
        static Employee employee3 = Employee.builder()
                .id(3)
                .name("test name1")
                .build();
    }

    @Test
    public void saveEmployeeTest() {
        when(employeeRepository.save(Samples.employee1)).thenReturn(Samples.employee1);

        Employee savedInstance = employeeRepository.save(Samples.employee1);
        Assertions.assertEquals("test name", savedInstance.getName());
    }

    @Test
    public void findByNameTest() {
        employeeRepository.save(Samples.employee1);
        when(employeeRepository.findByName(Samples.employee1.getName())).thenReturn(Samples.employee1);

        Employee foundInstance = employeeRepository.findByName(Samples.employee1.getName());
        Assertions.assertEquals(Samples.employee1, foundInstance);
    }

    @Test
    public void findAllTest() {
        Page<Employee> expected = new PageImpl<>(List.of(Samples.employee1, Samples.employee2, Samples.employee3));
        employeeRepository.saveAll(List.of(Samples.employee1, Samples.employee2, Samples.employee3));
        when(employeeRepository.findAll(Pageable.ofSize(5)))
                .thenReturn(new PageImpl<>(List.of(Samples.employee1, Samples.employee2, Samples.employee3)));

        Page<Employee> pagedEmployees = employeeRepository.findAll(Pageable.ofSize(5));
        Assertions.assertEquals(expected, pagedEmployees);
    }

    @Test
    public void findAllWithAddressesTest() {
        List<Employee> expected = List.of(Samples.employee2);
        when(employeeRepository.findEmployeeByPresentAddress()).thenReturn(List.of(Samples.employee2));

        List<Employee> result = employeeRepository.findEmployeeByPresentAddress();
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void findAllByPartOfTheNameEMTest() {
        List<Employee> expected = List.of(Samples.employee2, Samples.employee3);
        when(employeeRepositoryEMBean.findEmployeeByPartOfTheName("name1")).thenReturn(List.of(Samples.employee2, Samples.employee3));

        List<Employee> result = employeeRepositoryEMBean.findEmployeeByPartOfTheName("name1");
        Assertions.assertEquals(expected, result);
    }
}
