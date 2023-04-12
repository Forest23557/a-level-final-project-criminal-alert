package com.shulha.model;

import com.shulha.types.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "application_administrators")
public class Admin extends Person {
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
