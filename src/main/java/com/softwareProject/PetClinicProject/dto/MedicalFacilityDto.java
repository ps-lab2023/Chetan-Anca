package com.softwareProject.PetClinicProject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalFacilityDto {
    private long id;
    private String name;
    private int price;
}
