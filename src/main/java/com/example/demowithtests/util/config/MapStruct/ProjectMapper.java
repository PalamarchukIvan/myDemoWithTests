package com.example.demowithtests.util.config.MapStruct;

import com.example.demowithtests.domain.Project;
import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployee;
import com.example.demowithtests.dto.EmployeeDto.EmployeeReadDto;
import com.example.demowithtests.dto.PojectDto.ProjectRequestDto;
import com.example.demowithtests.dto.PojectDto.ProjectResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
    Project projectRequestDtoToProject(ProjectRequestDto projectRequestDto);
    ProjectResponseDto projectToProjectResponseDto(Project project);
    List<ProjectResponseDto> projectToProjectResponseDto(List<Project> projects);
    default Set<EmployeeReadDto> projectEmployeeToEmployeeSet( Set<ProjectEmployee> projectEmployeeSet) {
            if ( projectEmployeeSet == null ) {
                return null;
            }

            Set<EmployeeReadDto> resultSet = new LinkedHashSet<EmployeeReadDto>( Math.max( (int) ( projectEmployeeSet.size() / .75f ) + 1, 16 ) );
            for ( ProjectEmployee projectEmployee : projectEmployeeSet ) {
                EmployeeReadDto employeeReadDto = projectEmployeeToEmployeeReadDto( projectEmployee );
                if(employeeReadDto != null) resultSet.add( projectEmployeeToEmployeeReadDto( projectEmployee ) );
            }

            return resultSet;
    }

    private EmployeeReadDto projectEmployeeToEmployeeReadDto(ProjectEmployee projectEmployee) {
        return projectEmployee.getIsActive() ? EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(projectEmployee.getEmployee()) : null;
    }
}
