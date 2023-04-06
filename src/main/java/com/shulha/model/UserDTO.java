package com.shulha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDTO {
    private String id;
    private String phoneNumber;
    private String emailAddress;
    private String name;
    private String surname;

    @Override
    public String toString() {
        return String.format("User %s %s with ID - %s, phone number - %s and email address - %s",
                name, surname, id, phoneNumber, emailAddress);
    }
}
