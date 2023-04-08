package com.shulha.model;

import com.shulha.types.Role;
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
    private String name;
    private String surname;
    private Role role;
    private String phoneNumber;
    private String emailAddress;

    @Override
    public String toString() {
        return String.format("%s %s %s with ID - %s, phone number - %s and email address - %s",
                role.toString(), name, surname, id, phoneNumber, emailAddress);
    }
}
