package com.example.demowithtests.domain;

import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployee;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Language language;
    private LocalDate startDate = LocalDate.now();
    private LocalDate deadLine;
    private String backLog;
    private Boolean isPrivate = Boolean.FALSE;
    @OneToMany
    @JoinColumn(name = "project_id")
    private Set<ProjectEmployee> employees;

    public enum Language {
        JAVA, PYTHON, CPP, CSh, RUBY
    }
}
