package com.example.demowithtests.domain.ProjectsToEmployees;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectEmployeeKey implements Serializable {
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "employee_id")
    private Integer employeeId;
}