package com.example.demowithtests.dto.PojectDto;

import com.example.demowithtests.dto.EmployeeDto.EmployeeReadDto;

import java.time.LocalDate;
import java.util.Set;

public class ProjectResponseDto {
    public String language;
    public LocalDate startDate;
    public LocalDate deadLine;
    public String backLog;
    public Set<EmployeeReadDto> employees;

}
