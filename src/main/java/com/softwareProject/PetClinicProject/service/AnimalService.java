package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.AnimalNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAnimalException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AnimalService {
    Optional<Animal> findById(long id) throws AnimalNotFoundException;

    List<Animal> findAllByName(String name) throws AnimalNotFoundException;

    List<Animal> findAllByOwner(Owner owner);

    List<Animal> findAllByType(AnimalType animalType);

    List<Animal> findAllByAgeGraterThan(int age);

    List<Animal> findAll();

    Optional<Animal> addAnimal(Animal animal) throws InvalidAnimalException;

    Optional<Animal> updateAnimal(Animal animal) throws InvalidAnimalException, AnimalNotFoundException;

    void deleteById(long id) throws AnimalNotFoundException;
}
