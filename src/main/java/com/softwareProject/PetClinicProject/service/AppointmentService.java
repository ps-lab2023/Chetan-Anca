package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.AppointmentNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAppointmentException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.Doctor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public interface AppointmentService {
    Optional<Appointment> findById(long id) throws AppointmentNotFoundException;

    Optional<Appointment> findByDoctorAndTime(Doctor doctor, LocalDateTime time) throws AppointmentNotFoundException;

    List<Appointment> findAllByAnimal(Animal animal);

    List<Appointment> findAllByDoctor(Doctor doctor);

    List<Appointment> findAllByTime(LocalTime time);

    List<Appointment> findAll();

    Optional<Appointment> addAppointment(Appointment appointment) throws InvalidAppointmentException;

    Optional<Appointment> updateAppointmentTime(Appointment appointment) throws InvalidAppointmentException, AppointmentNotFoundException;

    void deleteById(long id) throws AppointmentNotFoundException;
}
