package com.softwareProject.PetClinicProject.dto;

import com.softwareProject.PetClinicProject.model.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private boolean logged;

    private LocalDateTime lastLoggedIn;
    private LocalDateTime lastLoggedOut;
}
