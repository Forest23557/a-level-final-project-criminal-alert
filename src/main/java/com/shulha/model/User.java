package com.shulha.model;

import com.shulha.types.Gender;
import com.shulha.types.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "application_users")
public class User extends Person {
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private double rating;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Contact> contacts;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Address> addresses;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Message> messages;
}
