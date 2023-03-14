package com.example.demowithtests.domain;

import com.example.demowithtests.util.anotations.Name;
import com.example.demowithtests.util.anotations.ShortenCountry;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")//устанавливает дэфолтное значение false
    private Boolean isPrivate = Boolean.FALSE;
    private String password;
}
