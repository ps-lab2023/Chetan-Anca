package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.AnimalNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAnimalException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.repository.AnimalRepository;
import com.softwareProject.PetClinicProject.service.AnimalService;
import com.softwareProject.PetClinicProject.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private OwnerService ownerService;

    public AnimalServiceImpl(AnimalRepository animalRepository, OwnerService ownerService) {
        this.animalRepository = animalRepository;
        this.ownerService = ownerService;
    }

    @Override
    public Optional<Animal> findById(long id) throws AnimalNotFoundException {
        Optional<Animal> animal = animalRepository.findById(id);
        if (!animal.isPresent()) {
            throw new AnimalNotFoundException("Animal with id " + id + " not found");
        }
        return animal;
    }

    @Override
    public List<Animal> findAllByName(String name) {
        return animalRepository.findAllByName(name);
    }

    @Override
    public List<Animal> findAllByOwner(Owner owner) {
        return animalRepository.findAllByOwner(owner);
    }

    @Override
    public List<Animal> findAllByType(AnimalType animalType) {
        return animalRepository.findAllByType(animalType);
    }

    @Override
    public List<Animal> findAllByAgeGraterThan(int age) {
        return animalRepository.findAllByAgeGreaterThan(age);
    }

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> addAnimal(Animal animal) throws InvalidAnimalException {
        try {
            ownerService.findById(animal.getOwner().getOwnerId());
        } catch (OwnerNotFoundException exp) {
            throw new InvalidAnimalException("Animal owner not found");
        }
        if (animal.getType() == null) {
            throw new InvalidAnimalException("Animal must have a type");
        }

        if (animal.getAge() == 0) {
            throw new InvalidAnimalException("Animal must have an age");
        }
        animal.getOwner().getAnimals().add(animal);
        animalRepository.save(animal);
        return Optional.of(animal);
    }

    @Override
    public Optional<Animal> updateAnimal(Animal animal) throws InvalidAnimalException, AnimalNotFoundException {
        Optional<Animal> animalToUpdate = animalRepository.findById(animal.getAnimalId());
        if (animalToUpdate.isPresent()) {
            if (animal.getOwner() != null) {
                try {
                    ownerService.findById(animal.getOwner().getOwnerId());
                } catch (OwnerNotFoundException exp) {
                    throw new InvalidAnimalException("Owner not found");
                }
                animalToUpdate.get().setOwner(animal.getOwner());
            }
            if (animal.getName() != null) {
                animalToUpdate.get().setName(animal.getName());
            }
            animalRepository.save(animalToUpdate.get());
        } else {
            throw new AnimalNotFoundException("Animal to update not found");
        }
        return animalToUpdate;
    }

    @Override
    public void deleteById(long id) throws AnimalNotFoundException {
        Optional<Animal> animalToDelete = animalRepository.findById(id);
        if (animalToDelete.isPresent()) {
            animalRepository.deleteById(id);
        } else {
            throw new AnimalNotFoundException("Animal to delete not found");
        }
    }
}
