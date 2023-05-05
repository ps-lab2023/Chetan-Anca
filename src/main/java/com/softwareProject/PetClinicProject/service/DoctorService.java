package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.DoctorNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidDoctorException;
import com.softwareProject.PetClinicProject.exception.InvalidOwnerException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DoctorService {
    Doctor getDoctorById(long id) throws DoctorNotFoundException;

    Doctor getDoctorByEmail(String email) throws DoctorNotFoundException;


    Doctor getDoctorByFirstNameAndLastName(String firstName, String lastName) throws DoctorNotFoundException;

    List<Doctor> getAllDoctorsByFirstName(String firstName);

    List<Doctor> getAllDoctorsByLastName(String lastName);

    Doctor getDoctorByEmailAndPassword(String email, String password) throws DoctorNotFoundException;

    List<Doctor> findAllDoctors();

    Doctor addDoctor(Doctor doctor) throws InvalidDoctorException;

    Doctor updateDoctor(Doctor doctor) throws InvalidDoctorException, DoctorNotFoundException;

    Doctor updatePassword(long id, String password) throws InvalidDoctorException, DoctorNotFoundException;

    void deleteDoctorById(long id) throws DoctorNotFoundException;
}
