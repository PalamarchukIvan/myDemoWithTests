package com.example.demowithtests.service.ProjectService;

import com.example.demowithtests.domain.Project;

import java.util.List;

public interface ProjectService {
    Project create(Project project);
    Project getById(Integer id);
    List<Project> getAll();
    Project updateById(Integer id, Project project);
    void removeById(Integer id);
    Project addEmployeeToProject(Integer idEmployee, Integer idProject);
    Project removeEmployeeFromProject(Integer idEmployee, Integer idProject);
}
