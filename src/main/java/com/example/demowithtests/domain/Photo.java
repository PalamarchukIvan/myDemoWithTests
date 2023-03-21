package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.io.File;
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
    private String path;
    private String name;
    private String format;
    private String url;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")
    private Boolean isPrivate = Boolean.FALSE;
}
