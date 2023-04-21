package com.softwareProject.PetClinicProject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MedicalFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int price;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "appointment_medical_facility",
//            joinColumns = @JoinColumn(name = "medical_facility_id"),
//            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
//    private List<Appointment> appointments = new ArrayList<>();
}
