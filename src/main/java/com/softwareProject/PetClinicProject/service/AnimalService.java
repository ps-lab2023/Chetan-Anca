package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.AnimalNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAnimalException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnimalService {
    Animal findById(long id) throws AnimalNotFoundException;

    List<Animal> findAllByName(String name) throws AnimalNotFoundException;

    List<Animal> findAllByOwner(Owner owner);

    List<Animal> findAllByType(AnimalType animalType);

    List<Animal> findAllByAgeGraterThan(int age);

    List<Animal> findAll();

    Animal addAnimal(Animal animal) throws InvalidAnimalException;

    Animal updateAnimal(Animal animal) throws InvalidAnimalException, AnimalNotFoundException;

    Animal updateAppointmentsList(Animal animal) throws AnimalNotFoundException;

    void deleteById(long id) throws AnimalNotFoundException;
}
