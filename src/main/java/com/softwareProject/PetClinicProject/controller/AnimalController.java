package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/findById")
    public ResponseEntity findAnimalById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findById(id));
    }

    @GetMapping("/findByName")
    public ResponseEntity findAllAnimalsByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAllByName(name));
    }

    @GetMapping("/findByOwner")
    public ResponseEntity findAllAnimalsByOwner(@RequestBody Owner owner) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAllByOwner(owner));
    }

    @GetMapping("/findByType")
    public ResponseEntity findAllAnimalsByType(@RequestParam AnimalType type) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAllByType(type));
    }

    @GetMapping("/findByAgeGrater")
    public ResponseEntity findAllAnimalsByAgeGraterThan(@RequestParam int age) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAllByAgeGraterThan(age));
    }

    @GetMapping()
    public ResponseEntity findAllAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity addAnimal(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.addAnimal(animal));
    }

    @PutMapping("/update")
    public ResponseEntity updateAnimal(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.updateAnimal(animal));
    }

    @DeleteMapping("/delete")
    public void deleteAnimal(@RequestParam long id) {
        animalService.deleteById(id);
    }
}
