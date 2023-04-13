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
@ToString
@Table(name = "user_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus;

    @Column(name = "to_email")
    private String toEmail;

    @Enumerated(EnumType.STRING)
    private EmailSubject subject;

    @Column(name = "crime_type")
    private CrimeTypes crimeType;

    @Column(name = "body")
    private String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
