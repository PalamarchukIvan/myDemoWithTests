package com.example.demowithtests.domain.ProjectsToEmployees;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "projects_employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectEmployee {
    @EmbeddedId
    private ProjectEmployeeKey key;
    @ManyToOne
    @JsonIgnore
    @MapsId("projectId")
    private Project project;
    @ManyToOne
    @MapsId("employeeId")
    private Employee employee;
    private Boolean isActive = Boolean.TRUE;

}
