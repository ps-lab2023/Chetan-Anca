package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.AnimalDto;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.service.AnimalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animal")
@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/findById")
    public ResponseEntity getAnimalById(@RequestParam long id) {
        Animal animal = animalService.getAnimalById(id);
        AnimalDto animalDto = modelMapper.map(animal, AnimalDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(animalDto);
    }

    @GetMapping("/findByName")
    public ResponseEntity getAllAnimalsByName(@RequestParam String name) {
        List<Animal> animals = animalService.getAllAnimalsByName(name);
        List<AnimalDto> animalsDto = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(animalsDto);
    }

    @GetMapping("/findByOwner")
    public ResponseEntity getAllAnimalsByOwner(@RequestParam long id) {
        List<Animal> animals = animalService.getAllByOwnerId(id);
        List<AnimalDto> animalsDto = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(animalsDto);
    }

    @GetMapping("/findByType")
    public ResponseEntity getAllAnimalsByType(@RequestParam AnimalType type) {
        List<Animal> animals = animalService.getAllAnimalsByType(type);
        List<AnimalDto> animalsDto = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(animalsDto);
    }

    @GetMapping("/findByAgeGrater")
    public ResponseEntity getAllAnimalsByAgeGraterThan(@RequestParam int age) {
        List<Animal> animals = animalService.getAllAnimalsByAgeGraterThan(age);
        List<AnimalDto> animalsDto = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(animalsDto);
    }

    @GetMapping("/findByWeightLess")
    public ResponseEntity getAllByWeightLessThan(@RequestParam float weight) {
        List<Animal> animals = animalService.getAllByWeightLessThan(weight);
        List<AnimalDto> animalsDto = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(animalsDto);
    }

    @GetMapping()
    public ResponseEntity getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        List<AnimalDto> animalsDto = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(animalsDto);
    }

    @PostMapping("/add")
    public ResponseEntity addAnimal(@RequestBody AnimalDto animalDto) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        Animal animalReturned = animalService.addAnimal(animal);
        AnimalDto animalDtoReturned = modelMapper.map(animalReturned, AnimalDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalDtoReturned);
    }

    @PutMapping("/update")
    public ResponseEntity updateAnimal(@RequestBody AnimalDto animalDto) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        Animal animalReturned = animalService.updateAnimal(animal);
        AnimalDto animalDtoReturned = modelMapper.map(animalReturned, AnimalDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(animalDtoReturned);
    }

    @DeleteMapping("/delete")
    public void deleteAnimal(@RequestParam long id) {
        animalService.deleteAnimalById(id);
    }
}
