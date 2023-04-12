package com.shulha.model;

import com.shulha.types.CrimeTypes;
import com.shulha.types.EmailSubject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "crime_message_body")
public class CrimeMessageBody extends SimpleMessageBody {
    @Column(name = "crime_type")
    @Enumerated(EnumType.STRING)
    private CrimeTypes crimeType;

    @Override
    public String getMessageBody() {
        return String.format("Type of crime: %s. Address: %s. Description: %s.",
                crimeType.toString(), super.getAddress().toString(), super.getDescription());
    }
}
