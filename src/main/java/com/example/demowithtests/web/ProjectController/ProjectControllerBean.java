package com.example.demowithtests.web.ProjectController;

import com.example.demowithtests.domain.Project;
import com.example.demowithtests.dto.PojectDto.ProjectRequestDto;
import com.example.demowithtests.dto.PojectDto.ProjectResponseDto;
import com.example.demowithtests.service.ProjectService.ProjectService;
import com.example.demowithtests.util.config.MapStruct.ProjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProjectControllerBean implements ProjectController{
    private final ProjectService projectService;
    @Override
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto create(@RequestBody ProjectRequestDto project) {
        return ProjectMapper.INSTANCE.projectToProjectResponseDto(
                projectService.create(
                        ProjectMapper.INSTANCE.projectRequestDtoToProject(project)));
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto readProjectById(@PathVariable Integer id) {
        return ProjectMapper.INSTANCE.projectToProjectResponseDto(
                projectService.getById(id));
    }

    @Override
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectResponseDto> readAllProjects() {
        return ProjectMapper.INSTANCE.projectToProjectResponseDto(
                projectService.getAll());
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeProject(@PathVariable Integer id) {
        projectService.removeById(id);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto patchProject(@PathVariable Integer id, @RequestBody ProjectRequestDto toUpdateProject) {
        return ProjectMapper.INSTANCE.projectToProjectResponseDto(
                projectService.updateById(id,
                        ProjectMapper.INSTANCE.projectRequestDtoToProject(toUpdateProject)));
    }

    @Override
    @PatchMapping("/{idProject}/employee/{idEmployee}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto addEmployeeToProject(@PathVariable Integer idEmployee, @PathVariable Integer idProject) {
        return ProjectMapper.INSTANCE.projectToProjectResponseDto(
                projectService.addEmployeeToProject(idEmployee, idProject));
    }
}
