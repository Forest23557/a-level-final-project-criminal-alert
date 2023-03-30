package com.shulha.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class User extends Person {
    private String phoneNumber;
    private String age;
    private LocalDate dateOfBirth;
    private String address;
}
