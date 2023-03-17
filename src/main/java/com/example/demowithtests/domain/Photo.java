package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

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
    private Date uploadDate = Date.from(Instant.now());
    private String description;
    private String cameraType;
    private String photoUrl;
}
