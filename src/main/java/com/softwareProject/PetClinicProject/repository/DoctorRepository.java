package com.softwareProject.PetClinicProject.repository;

import com.softwareProject.PetClinicProject.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findById(long id);
    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Doctor> findByEmailAndPassword(String email, String password);

    List<Doctor> findAll();
}
