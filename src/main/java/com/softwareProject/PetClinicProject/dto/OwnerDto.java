package com.softwareProject.PetClinicProject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private long ownerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}
