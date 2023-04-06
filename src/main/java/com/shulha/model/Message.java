package com.shulha.model;

import com.shulha.types.EmailSubject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "to_email")
    private String toEmail;
    @Enumerated(EnumType.STRING)
    private EmailSubject subject;
    private String body;
}
