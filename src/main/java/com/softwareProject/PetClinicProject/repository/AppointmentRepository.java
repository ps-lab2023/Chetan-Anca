package com.softwareProject.PetClinicProject.repository;

import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(long id);

    Optional<Appointment> findByDoctorFirstNameAndDoctorLastNameAndDate(String firstName, String lastName, LocalDateTime date);

    List<Appointment> findAllByDoctorFirstNameAndDoctorLastName(String firstName, String lastName);

    List<Appointment> findAllByAnimalOwnerOwnerId(long id);

    List<Appointment> findAllByDoctorDoctorId(long id);

    List<Appointment> findAllByAnimalName(String name);

    List<Appointment> findAllByDate(LocalDateTime date);

    List<Appointment> findAll();
}
