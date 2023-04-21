package com.softwareProject.PetClinicProject.dto;

import com.softwareProject.PetClinicProject.model.UserType;
import jakarta.persistence.*;
import lombok.*;

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
}
