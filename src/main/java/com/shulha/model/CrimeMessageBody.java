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
@Table(name = "crime_message_body")
public class CrimeMessageBody extends SimpleMessageBody {
    @Column(name = "crime_type")
    @Enumerated(EnumType.STRING)
    private CrimeTypes crimeType;
    @OneToOne
    private Address address;
}
