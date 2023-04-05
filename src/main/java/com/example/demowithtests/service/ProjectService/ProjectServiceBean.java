package com.example.demowithtests.service.ProjectService;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Project;
import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployee;
import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployeeKey;
import com.example.demowithtests.dto.PojectDto.ProjectResponseDto;
import com.example.demowithtests.repository.ProjectEmployeeRepository;
import com.example.demowithtests.repository.ProjectRepository;
import com.example.demowithtests.service.EmployeeService.EmployeeService;
import com.example.demowithtests.util.exception.ResourceException;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceBean implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeService employeeService;
    private final ProjectEmployeeRepository projectEmployeeRepository;

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getById(Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(ResourceException::new);
        if (project.getIsPrivate()) throw new ResourceIsPrivateException();
        return project;
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll()
                .stream()
                .filter(project -> !project.getIsPrivate())
                .collect(Collectors.toList());
    }

    @Override
    public Project updateById(Integer id, Project update) {
        return projectRepository.findById(id)
                .map(project -> {
                    if (update == null) return project;

                    if (update.getLanguage() != project.getLanguage())
                        project.setLanguage(update.getLanguage());

                    if (update.getDeadLine() != project.getDeadLine())
                        project.setDeadLine(project.getDeadLine());

                    if (!Objects.equals(update.getBackLog(), project.getBackLog()))
                        project.setBackLog(update.getBackLog());

                    if (update.getStartDate() != project.getStartDate())
                        project.setStartDate(update.getStartDate());

                    if (update.getDeadLine() != project.getDeadLine())
                        project.setDeadLine(update.getDeadLine());

                    projectRepository.save(project);
                    return project;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void removeById(Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        project.setIsPrivate(Boolean.TRUE);
        projectRepository.save(project);

    }

    @Override
    public Project addEmployeeToProject(Integer idEmployee, Integer idProject) {
        Employee employee = employeeService.getById(idEmployee);
        Project project = getById(idProject);

        ProjectEmployee projectEmployee = ProjectEmployee.builder()
                .key(ProjectEmployeeKey.builder()
                        .projectId(idProject)
                        .employeeId(idEmployee)
                        .build())
                .employee(employee)
                .project(project)
                .isActive(Boolean.TRUE)
                .build();
        projectEmployeeRepository.save(projectEmployee);
        project.getEmployees().add(projectEmployee);
        return projectRepository.save(project);
    }

    @Override
    public Project removeEmployeeFromProject(Integer idEmployee, Integer idProject) {
        ProjectEmployee projectEmployee = projectEmployeeRepository
                .findById(ProjectEmployeeKey.builder()
                                .employeeId(idEmployee)
                                .projectId(idProject)
                                .build())
                .orElseThrow(ResourceNotFoundException::new);
        projectEmployee.setIsActive(Boolean.FALSE);
        projectEmployeeRepository.save(projectEmployee);
        return projectEmployee.getProject();
    }
}
