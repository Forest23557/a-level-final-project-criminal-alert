package com.shulha.model;

import com.shulha.types.Gender;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String login;
    private String password;
    @Column(name = "email_address")
    private String emailAddress;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
