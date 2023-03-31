package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidOwnerException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OwnerService {
    Owner findById(long id) throws OwnerNotFoundException;

    Owner findByEmail(String email) throws OwnerNotFoundException;

    Owner findByFirstNameAndLastName(String firstName, String lastName) throws OwnerNotFoundException;

    Owner findByEmailAndPassword(String email, String password) throws OwnerNotFoundException;

    List<Owner> findAll();

    Owner addOwner(Owner owner) throws InvalidOwnerException;

    Owner updateOwner(Owner owner) throws InvalidOwnerException, OwnerNotFoundException;

    Owner updateAnimalsList(Owner owner) throws OwnerNotFoundException;

    void deleteById(long id) throws OwnerNotFoundException;

}
