package com.shulha.model;

import com.shulha.types.AddressStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_addresses")
@ToString(callSuper = true)
public class Address extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "address_status")
    private AddressStatus addressStatus;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city_or_town")
    private String cityOrTown;

    @Column(name = "district")
    private String district;

    @Column(name = "address_line")
    private String addressLine;
}
