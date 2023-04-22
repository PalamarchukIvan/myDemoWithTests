package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Project;
import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployee;
import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployeeKey;
import com.example.demowithtests.repository.ProjectEmployeeRepository;
import com.example.demowithtests.repository.ProjectRepository;
import com.example.demowithtests.service.EmployeeService.EmployeeService;
import com.example.demowithtests.service.ProjectService.ProjectServiceBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ServiceProjectTests {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private ProjectEmployeeRepository projectEmployeeRepository;
    @InjectMocks
    private ProjectServiceBean projectServiceBean;

    private static class Samples {
        static Employee employee = Employee.builder()
                .id(1)
                .name("Some Test Name")
                .isPrivate(Boolean.FALSE)
                .build();

        static Project project = Project.builder()
                .id(1)
                .language(Project.Language.JAVA)
                .isPrivate(Boolean.FALSE)
                .build();
    }

    @Test
    public void addEmployeeToProjectTest() {
        Project expected = Project.builder()
                .id(1)
                .language(Project.Language.JAVA)
                .isPrivate(Boolean.FALSE)
                .build();
        expected.setEmployees(Set.of(new ProjectEmployee(ProjectEmployeeKey.builder()
                .projectId(expected.getId())
                .employeeId(Samples.employee.getId())
                .build() , expected, Samples.employee, true)));
        ProjectEmployee projectEmployee = ProjectEmployee.builder()
                .key(ProjectEmployeeKey.builder()
                        .projectId(1)
                        .employeeId(1)
                        .build())
                .employee(Samples.employee)
                .project(Samples.project)
                .isActive(Boolean.TRUE)
                .build();

        when(employeeService.getById(1)).thenReturn(Samples.employee);
        when(projectRepository.findById(1)).thenReturn(Optional.of(Samples.project));
        when(projectEmployeeRepository.save(projectEmployee)).thenReturn(projectEmployee);

        Project result = projectServiceBean.addEmployeeToProject(1, 1);
        Assertions.assertEquals(expected.getId(), result.getId());
        verify(projectEmployeeRepository).save(any(ProjectEmployee.class));
    }
}
