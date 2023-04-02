package com.example.demowithtests.util.config.MapStruct;

import com.example.demowithtests.domain.Project;
import com.example.demowithtests.dto.PojectDto.ProjectRequestDto;
import com.example.demowithtests.dto.PojectDto.ProjectResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
    Project projectRequestDtoToProject(ProjectRequestDto projectRequestDto);
    ProjectResponseDto projectToProjectResponseDto(Project project);
    List<ProjectResponseDto> projectToProjectResponseDto(List<Project> projects);
}
