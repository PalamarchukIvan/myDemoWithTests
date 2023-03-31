package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

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
    private String key  = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    private State currentState = State.ACTIVE;
    @OneToOne(mappedBy = "badge")
    private Employee employee;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")
    private Boolean isPrivate = Boolean.FALSE;
    @OneToOne()
    @JoinColumn(name = "previous_id")
    private Badge previousBadge;

    public enum State {
        ACTIVE, LOST, BROKEN, EXPIRED, ANOTHER;
    }

}
