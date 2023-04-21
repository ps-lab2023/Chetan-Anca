package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.AnimalNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAnimalException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnimalService {
    Animal getAnimalById(long id) throws AnimalNotFoundException;

    List<Animal> getAllAnimalsByName(String name) throws AnimalNotFoundException;

    List<Animal> getAllByOwnerFirstNameAndOwnerLastName(String firstName, String lastName);

    List<Animal> getAllByOwnerId(long ownerId);

    List<Animal> getAllAnimalsByType(AnimalType animalType);

    List<Animal> getAllAnimalsByAgeGraterThan(int age);

    List<Animal> getAllByWeightLessThan(float weight);

    List<Animal> getAllAnimals();

    Animal addAnimal(Animal animal) throws InvalidAnimalException;

    Animal updateAnimal(Animal animal) throws InvalidAnimalException, AnimalNotFoundException;

    void deleteAnimalById(long id) throws AnimalNotFoundException;
}
