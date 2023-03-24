package com.softwareProject.PetClinicProject.repository;

import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findById(long id);

    List<Animal> findAllByName(String name);

    List<Animal> findAllByOwner(Owner owner);

    List<Animal> findAllByType(AnimalType animalType);

    List<Animal> findAllByAgeGreaterThan(int age);

    List<Animal> findAll();
}
