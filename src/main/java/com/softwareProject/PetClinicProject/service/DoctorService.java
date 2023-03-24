package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.DoctorNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidDoctorException;
import com.softwareProject.PetClinicProject.model.Doctor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DoctorService {
    Optional<Doctor> findById(long id) throws DoctorNotFoundException;

    Optional<Doctor> findByEmail(String email) throws DoctorNotFoundException;


    Optional<Doctor> findByFirstNameAndLastName(String firstName, String lastName) throws DoctorNotFoundException;


    Optional<Doctor> findByEmailAndPassword(String email, String password) throws DoctorNotFoundException;

    List<Doctor> findAll();

    Optional<Doctor> addDoctor(Doctor doctor) throws InvalidDoctorException;

    Optional<Doctor> updateDoctor(Doctor doctor) throws InvalidDoctorException, DoctorNotFoundException;

    void deleteById(long id) throws DoctorNotFoundException;
}
