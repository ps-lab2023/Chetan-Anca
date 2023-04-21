package com.softwareProject.PetClinicProject.dto;

import com.softwareProject.PetClinicProject.model.AnimalType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private long animalId;
    private String name;
    private OwnerDto owner;
    @Enumerated(EnumType.STRING)
    private AnimalType type;
    private String breed;
    private int age;
    private float weight;
}
