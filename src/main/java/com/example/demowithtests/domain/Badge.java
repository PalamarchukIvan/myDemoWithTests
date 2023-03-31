package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "badges")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Builder
public class Badge {
    @Id
    @JoinColumn(name = "previous_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    @OneToOne(mappedBy = "badge")
    private Employee employee;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")
    private Boolean isPrivate = Boolean.FALSE;
    @OneToOne()
    @JoinColumn(name = "previous_id")
    private Badge previousBadge;

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
