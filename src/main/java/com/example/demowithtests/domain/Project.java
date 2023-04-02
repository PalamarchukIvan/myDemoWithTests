package com.example.demowithtests.domain;

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
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public enum Language {
        JAVA, PYTHON, CPP, CSh, RUBY
    }
}
