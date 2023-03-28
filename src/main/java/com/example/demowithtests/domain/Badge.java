package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bagdes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Builder
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    @OneToOne(mappedBy = "badge")
    private Employee employee;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")
    private Boolean isPrivate = Boolean.FALSE;

    @Override
    public String toString() {
        String sEmployee = "";
        if(employee != null) {
            sEmployee = employee.getName();
        }
        return "Badge{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", employee=" + sEmployee +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
