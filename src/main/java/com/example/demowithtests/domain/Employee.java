package com.example.demowithtests.domain;

import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployee;
import com.example.demowithtests.util.anotations.formatingAnnotations.Name;
import com.example.demowithtests.util.anotations.formatingAnnotations.ShortenCountry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Name
    private String name;
    @ShortenCountry
    private String country;
    private String email;
    private String phone;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<Photo> photos = new LinkedList<>();
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean isPrivate = Boolean.FALSE;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "badge_id", referencedColumnName = "id")
    private Badge badge;
    @OneToMany(mappedBy = "employee")
    //@JoinColumn(name = "employee_id")
    private Set<ProjectEmployee> projects;
}