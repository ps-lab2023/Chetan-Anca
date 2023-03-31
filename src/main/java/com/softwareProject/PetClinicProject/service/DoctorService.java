package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.DoctorNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidDoctorException;
import com.softwareProject.PetClinicProject.model.Doctor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DoctorService {
    Doctor findById(long id) throws DoctorNotFoundException;

    Doctor findByEmail(String email) throws DoctorNotFoundException;


    Doctor findByFirstNameAndLastName(String firstName, String lastName) throws DoctorNotFoundException;


    Doctor findByEmailAndPassword(String email, String password) throws DoctorNotFoundException;

    List<Doctor> findAll();

    Doctor addDoctor(Doctor doctor) throws InvalidDoctorException;

    Doctor updateDoctor(Doctor doctor) throws InvalidDoctorException, DoctorNotFoundException;

    Doctor updateAppointmentsList(Doctor doctor) throws DoctorNotFoundException;

    void deleteById(long id) throws DoctorNotFoundException;
}
