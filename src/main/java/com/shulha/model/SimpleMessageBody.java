package com.shulha.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "simple_message_body")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SimpleMessageBody {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String description;

    @Override
    public String toString() {
        return String.format("%s", description);
    }
}
