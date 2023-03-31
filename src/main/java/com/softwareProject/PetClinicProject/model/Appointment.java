package com.softwareProject.PetClinicProject.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime time;

    @Override
    @Transactional
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", doctor=" + doctor.toString() +
                ", animal=" + animal.toString() +
                '}';
    }
}
