package com.shulha.model;

import com.shulha.types.RelationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_contacts")
public class Contact extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type")
    private RelationType relationType;
}
