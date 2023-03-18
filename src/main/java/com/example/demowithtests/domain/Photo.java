package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photo")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Builder
@ToString
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @EqualsAndHashCode.Exclude
    private LocalDate uploadDate = LocalDate.now();
    private String description;
    private String cameraType;
    private String photoUrl;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")
    private Boolean isPrivate = Boolean.FALSE;
}
