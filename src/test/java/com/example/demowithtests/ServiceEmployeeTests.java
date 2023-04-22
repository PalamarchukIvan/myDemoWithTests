package com.example.demowithtests;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.repository.BadgeRepository;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.BadgeService.BadgeService;
import com.example.demowithtests.service.BadgeService.BadgeServiceBean;
import com.example.demowithtests.service.EmailService.EmailServiceBean;
import com.example.demowithtests.service.EmployeeService.EmployeeServiceBean;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ServiceEmployeeTests {

    @Mock
    private EmployeeRepository repository;
    @Mock
    private EmailServiceBean emailService;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private BadgeServiceBean badgeServiceBean;
    @InjectMocks
    private EmployeeServiceBean service;

    private static class Samples {
        static Employee employee1 = Employee.builder()
                .id(1)
                .name("test name")
                .isPrivate(Boolean.TRUE)
                .build();
        static Employee employee2 = Employee.builder()
                .id(3)
                .name("test name1")
                .isPrivate(Boolean.FALSE)
                .build();
        static Employee employee3 = Employee.builder()
                .id(3)
                .name("test name1")
                .isPrivate(Boolean.FALSE)
                .photos(List.of(Photo.builder()
                        .uploadDate(LocalDate.now().minusYears(6))
                        .isPrivate(Boolean.FALSE)
                        .build()))
                .build();
        static Employee employeeForRepost = Employee.builder()
                .name("reposted")
                .build();
    }

    @Test
    public void createTest() {
        when(repository.save(Samples.employee1)).thenReturn(Samples.employee1);
        Employee result = service.create(Samples.employee1);
        Assertions.assertEquals(Samples.employee1, result);
        verify(repository).save(Samples.employee1);
    }

    @Test
    public void getAllTest() {
        List<Employee> expected = List.of(Samples.employee2, Samples.employee3);
        when(repository.findAll()).thenReturn(expected);

        List<Employee> result = service.getAll();
        Assertions.assertEquals(expected, result);
        verify(repository).findAll();
    }

    @Test
    public void getByIdTest() {
        when(repository.findById(1)).thenReturn(Optional.of(Samples.employee1));
        Employee result = service.getById(1);
        Assertions.assertEquals(Samples.employee1, result);
        verify(repository).findById(1);
    }

    @Test
    public void repostIdTest() {
        when(repository.findById(1)).thenReturn(Optional.of(Samples.employee1));
        when(repository.save(any(Employee.class))).thenReturn(Samples.employeeForRepost);
        Employee result = service.repostById(1, Samples.employeeForRepost);

        Assertions.assertEquals(Samples.employeeForRepost, result);
        verify(repository).findById(1);
    }

    @Test
    public void patchByIdTest() {
        Employee expected = Samples.employee1;
        expected.setName(Samples.employeeForRepost.getName());
        when(repository.findById(1)).thenReturn(Optional.of(Samples.employee1));
        when(repository.save(any(Employee.class))).thenReturn(expected);
        Employee result = service.patchById(1, Samples.employeeForRepost);

        Assertions.assertEquals(expected, result);
        verify(repository).findById(1);
    }

    @Test
    public void updateEmployeesWithExpiredPhotosTest() {
        List<Employee> expected = List.of(Samples.employee3);
        when(repository.findAll()).thenReturn(List.of(Samples.employee2, Samples.employee3));
        when(repository.findById(3)).thenReturn(Optional.of(Samples.employee3));
        when(repository.saveAll(any())).thenReturn(expected);
        when(emailService.sendTestMail(any(String.class), any(String.class), any(String.class))).thenReturn(true);
        List<Employee> result = service.updateEmployeesWithExpiredPhotos();
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void addBadgeTest() {
        Badge badge = Badge.builder()
                .employee(Samples.employee1)
                .id(1)
                .build();
        Employee expected = Samples.employee1;
        expected.setBadge(badge);
        when(repository.findById(1)).thenReturn(Optional.of(Samples.employee1));
        when(badgeServiceBean.getById(1)).thenReturn(badge);
        when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

        Employee result = service.addBadge(1, badge);
        Assertions.assertEquals(expected, result);
    }
}
