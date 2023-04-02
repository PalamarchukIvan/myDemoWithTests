package com.example.demowithtests.service.ProjectService;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Project;
import com.example.demowithtests.repository.ProjectRepository;
import com.example.demowithtests.service.EmployeeSeeervice.EmployeeService;
import com.example.demowithtests.util.exception.ResourceException;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceBean implements ProjectService{
    private final ProjectRepository projectRepository;
    private final EmployeeService employeeService;
    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getById(Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(ResourceException::new);
        if(project.getIsPrivate()) throw new ResourceIsPrivateException();
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
                    project.setLanguage(update.getLanguage());
                    project.setDeadLine(project.getDeadLine());
                    project.setBackLog(update.getBackLog());
                    project.setStartDate(update.getStartDate());
                    project.setDeadLine(update.getDeadLine());
                    projectRepository.save(project);
                    return project;
                } )
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

        project.getEmployees().add(employee);
        return projectRepository.save(project);
    }
}
