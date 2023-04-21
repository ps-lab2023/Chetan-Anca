package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.AnimalNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAnimalException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
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
    public Animal getAnimalById(long id) throws AnimalNotFoundException {
        Optional<Animal> animal = animalRepository.findById(id);
        if (!animal.isPresent()) {
            throw new AnimalNotFoundException("Animal with id " + id + " not found");
        }
        return animal.get();
    }

    @Override
    public List<Animal> getAllAnimalsByName(String name) {
        return animalRepository.findAllByName(name);
    }

    @Override
    public List<Animal> getAllByOwnerFirstNameAndOwnerLastName(String firstName, String lastName) {
        return animalRepository.findAllByOwnerFirstNameAndOwnerLastName(firstName, lastName);
    }

    @Override
    public List<Animal> getAllByOwnerId(long ownerId) {
        return animalRepository.findAllByOwnerOwnerId(ownerId);
    }

    @Override
    public List<Animal> getAllAnimalsByType(AnimalType animalType) {
        return animalRepository.findAllByType(animalType);
    }

    @Override
    public List<Animal> getAllAnimalsByAgeGraterThan(int age) {
        return animalRepository.findAllByAgeGreaterThan(age);
    }

    @Override
    public List<Animal> getAllByWeightLessThan(float weight) {
        return animalRepository.findAllByWeightLessThan(weight);
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Animal addAnimal(Animal animal) throws InvalidAnimalException {
        try {
            ownerService.getOwnerById(animal.getOwner().getOwnerId());
        } catch (OwnerNotFoundException exp) {
            throw new InvalidAnimalException("Animal owner not found");
        }
        if (animal.getName() == null) {
            throw new InvalidAnimalException("Animal must have a name");
        }
        if (animal.getType() == null) {
            throw new InvalidAnimalException("Animal must have a type");
        }
        if (animal.getAge() == 0) {
            throw new InvalidAnimalException("Animal must have an age");
        }
        if (animal.getWeight() == 0) {
            throw new InvalidAnimalException("Animal must have weight");
        }
        animalRepository.save(animal);
        return animal;
    }

    @Override
    public Animal updateAnimal(Animal animal) throws InvalidAnimalException, AnimalNotFoundException {
        Optional<Animal> animalToUpdate = animalRepository.findById(animal.getAnimalId());
        if (animalToUpdate.isPresent()) {
            if (!animal.getName().equals(animalToUpdate.get().getName())) {
                animalToUpdate.get().setName(animal.getName());
            }
            if (animal.getAge() != animalToUpdate.get().getAge()) {
                animalToUpdate.get().setAge(animal.getAge());
            }
            if (animal.getWeight() != animalToUpdate.get().getWeight()) {
                animalToUpdate.get().setWeight(animal.getWeight());
            }
            animalRepository.save(animalToUpdate.get());
        } else {
            throw new AnimalNotFoundException("Animal to update not found");
        }
        return animalToUpdate.get();
    }


    @Override
    public void deleteAnimalById(long id) throws AnimalNotFoundException {
        Optional<Animal> animalToDelete = animalRepository.findById(id);
        if (animalToDelete.isPresent()) {
            animalRepository.deleteById(id);
        } else {
            throw new AnimalNotFoundException("Animal to delete not found");
        }
    }
}
