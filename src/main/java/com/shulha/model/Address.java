package com.shulha.model;

import com.shulha.types.Country;
import com.shulha.types.Region;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "city_or_town")
    private String cityOrTown;

    @Column(name = "district")
    private String district;

    @Column(name = "address_line")
    private String addressLine;
}
