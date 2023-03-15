package com.example.demowithtests.domain;

import com.example.demowithtests.util.anotations.formatingAnnotations.ShortenCountry;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "address_has_active")
    private Boolean addressHasActive = Boolean.TRUE;
    @Column(name = "country")
    @ShortenCountry
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
}
