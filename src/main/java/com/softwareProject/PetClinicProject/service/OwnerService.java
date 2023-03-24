package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidOwnerException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface OwnerService {
    Optional<Owner> findById(long id) throws OwnerNotFoundException;

    Optional<Owner> findByEmail(String email) throws OwnerNotFoundException;

    Optional<Owner> findByFirstNameAndLastName(String firstName, String lastName) throws OwnerNotFoundException;

    Optional<Owner> findByEmailAndPassword(String email, String password) throws OwnerNotFoundException;

    List<Owner> findAll();

    Optional<Owner> addOwner(Owner owner) throws InvalidOwnerException;

    Optional<Owner> updateOwner(Owner owner) throws InvalidOwnerException, OwnerNotFoundException;

    void deleteById(long id) throws OwnerNotFoundException;

}
