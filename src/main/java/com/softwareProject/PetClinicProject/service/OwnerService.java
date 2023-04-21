package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidOwnerException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OwnerService {
    Owner getOwnerById(long id) throws OwnerNotFoundException;

    Owner getOwnerByEmail(String email) throws OwnerNotFoundException;

    Owner getOwnerByFirstNameAndLastName(String firstName, String lastName) throws OwnerNotFoundException;

    List<Owner> getAllByFirstNameContaining(String firstName);

    List<Owner> getAllByLastNameContaining(String lastName);


    Owner getOwnerByEmailAndPassword(String email, String password) throws OwnerNotFoundException;

    List<Owner> getAllOwners();

    Owner addOwner(Owner owner) throws InvalidOwnerException;

    Owner updateOwner(Owner owner) throws InvalidOwnerException, OwnerNotFoundException;

    void deleteOwnerById(long id) throws OwnerNotFoundException;
}
