package com.softwareProject.PetClinicProject.repository;

import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findById(long id);

    Optional<Owner> findByEmail(String email);

    Optional<Owner> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Owner> findByEmailAndPassword(String email, String password);

    List<Owner> findAll();
}
