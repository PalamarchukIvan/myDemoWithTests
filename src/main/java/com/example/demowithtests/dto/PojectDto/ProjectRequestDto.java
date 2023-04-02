package com.example.demowithtests.dto.PojectDto;

import com.example.demowithtests.domain.Project;

import java.time.LocalDate;

public class ProjectRequestDto {
    public Project.Language language;
    public LocalDate startDate = LocalDate.now();
    public LocalDate deadLine;
    public Boolean isPrivate = Boolean.FALSE;
    public String backLog;
}
