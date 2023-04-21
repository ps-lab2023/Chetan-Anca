package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.AppointmentNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAppointmentException;
import com.softwareProject.PetClinicProject.model.Appointment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface AppointmentService {
    Appointment getAppointmentById(long id) throws AppointmentNotFoundException;

    Appointment getByDoctorFirstNameAndDoctorLastNameAndDate(String firstName, String lastName, LocalDateTime date) throws AppointmentNotFoundException;

    List<Appointment> getAllByAnimalName(String name);

    List<Appointment> getAllByDoctorFirstNameAndDoctorLastName(String firstName, String lastName);

    List<Appointment> getAllAppointmentsByTime(LocalDateTime date);

    List<Appointment> getAllByAnimalOwnerId(long id);

    List<Appointment> getAllByDoctorId(long id);


    List<Appointment> getAppointmentsAll();

    Appointment addAppointment(Appointment appointment) throws InvalidAppointmentException;

    Appointment updateAppointment(Appointment appointment) throws InvalidAppointmentException, AppointmentNotFoundException;

    void deleteAppointmentById(long id) throws AppointmentNotFoundException;
}
