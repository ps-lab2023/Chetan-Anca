package com.softwareProject.PetClinicProject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private long appointmentId;
    private DoctorDto doctor;
    private AnimalDto animal;
    private LocalDateTime date;
    private List<MedicalFacilityDto> medicalFacilities;
}
