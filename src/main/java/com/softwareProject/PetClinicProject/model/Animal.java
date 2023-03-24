package com.softwareProject.PetClinicProject.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalId;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Owner owner;
    @Enumerated(EnumType.STRING)
    private AnimalType type;
    private String breed;
    private int age;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "animal")
    private List<Appointment> appointments;

    public Animal() {
        appointments = new ArrayList<>();
    }

    @Override
    @Transactional
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", name='" + name + '\'' +
                ", owner=" + owner.toString() +
                ", type=" + type +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                '}';
    }
}
