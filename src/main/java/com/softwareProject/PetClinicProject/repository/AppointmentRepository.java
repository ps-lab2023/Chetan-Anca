package com.softwareProject.PetClinicProject.repository;

import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(long id);

    Optional<Appointment> findByDoctorAndTime(Doctor doctor, LocalDateTime time);

    List<Appointment> findAllByDoctor(Doctor doctor);

    List<Appointment> findAllByAnimal(Animal animal);

    List<Appointment> findAllByTime(LocalTime time);

    List<Appointment> findAll();
}
