package com.softwareProject.PetClinicProject.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long appointmentId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Animal animal;
    private LocalDateTime date;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<MedicalFacility> medicalFacilities = new ArrayList<>();

    @Override
    @Transactional
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", doctor=" + doctor.toString() +
                ", animal=" + animal.toString() +
                '}';
    }

    private int computePrice() {
        int price = 0;
        for (MedicalFacility medicalFacility : medicalFacilities) {
            price += medicalFacility.getPrice();
        }
        return price;
    }
}
