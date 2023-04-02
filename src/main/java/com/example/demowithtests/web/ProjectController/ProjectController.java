package com.example.demowithtests.web.ProjectController;

import com.example.demowithtests.domain.Project;
import com.example.demowithtests.dto.PojectDto.ProjectRequestDto;
import com.example.demowithtests.dto.PojectDto.ProjectResponseDto;

import java.util.List;

public interface ProjectController {
    ProjectResponseDto create(ProjectRequestDto project);
    ProjectResponseDto readProjectById(Integer id);
    List<ProjectResponseDto> readAllProjects();
    void removeProject(Integer id);
    ProjectResponseDto patchProject(Integer id, ProjectRequestDto toUpdateProject);
    ProjectResponseDto addEmployeeToProject(Integer idEmployee, Integer idProject);
}
