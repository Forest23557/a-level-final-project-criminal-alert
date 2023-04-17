package com.shulha.model;

import com.shulha.types.CrimeTypes;
import com.shulha.types.EmailSubject;
import com.shulha.types.MessageStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "user_messages")
public class Message extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus;

    @Column(name = "to_email")
    private String toEmail;

    @Enumerated(EnumType.STRING)
    private EmailSubject subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "crime_type")
    private CrimeTypes crimeType;

    @Column(name = "body")
    private String body;
}
