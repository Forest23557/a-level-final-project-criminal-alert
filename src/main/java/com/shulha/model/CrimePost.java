package com.shulha.model;

import com.shulha.types.CrimeTypes;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "crime_posts")
public class CrimePost extends BaseEntity {

    @Column(name = "crime_type")
    private CrimeTypes crimeType;

    @Column(name = "header")
    private String header;

    @Column(name = "body")
    private String body;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
