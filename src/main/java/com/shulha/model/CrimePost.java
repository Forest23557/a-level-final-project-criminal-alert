package com.shulha.model;

import com.shulha.types.CrimeTypes;
import com.shulha.types.PostStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "crime_posts")
public class CrimePost extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "crime_type")
    private CrimeTypes crimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private PostStatus postStatus;

    @Column(name = "header")
    private String header;

    @Column(name = "body")
    private String body;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
