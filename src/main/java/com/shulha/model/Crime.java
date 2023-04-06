package com.shulha.model;

import com.shulha.types.CrimeTypes;
import com.shulha.types.EmailSubject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "crime")
public class Crime {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Enumerated(EnumType.STRING)
    private EmailSubject subject;
    @Column(name = "crime_type")
    @Enumerated(EnumType.STRING)
    private CrimeTypes crimeType;
    @OneToOne
    private User sender;
    @OneToOne
    private Address address;
    private String description;
}
