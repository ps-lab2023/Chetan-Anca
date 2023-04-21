package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.*;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.AnimalType;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.repository.AnimalRepository;
import com.softwareProject.PetClinicProject.service.impl.AnimalServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnimalServiceTest {
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private OwnerService ownerService;
    private AnimalServiceImpl animalService;
    private Animal animal;

    @Before
    public void setUp() {
        initMocks(this);
        animalService = new AnimalServiceImpl(animalRepository, ownerService);
        animal = new Animal();
    }

    @Test
    public void givenExistingAnimalId_whenFindById_thenFindAnimal() {
        animal.setAnimalId(1L);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        Animal foundAnimal = animalService.getAnimalById(animal.getAnimalId());

        assertNotNull(foundAnimal);
        assertEquals(1L, foundAnimal.getAnimalId());
    }

    @Test
    public void givenNonExistingAnimalId_whenFindById_thenFindAnimal() {
        animal.setAnimalId(400L);

        when(animalRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(AnimalNotFoundException.class, () -> {
            animalService.getAnimalById(animal.getAnimalId());
        });
    }

    @Test
    public void givenOwner_whenFindAllByOwner_thenFindAnimals() {
        Owner owner = new Owner(1L, "John", "Smith", "johnsmith@yahoo.com",
                "MyPassword.12", "0745382312", List.of(animal));
        animal.setAnimalId(1L);
        animal.setOwner(owner);

        when(animalRepository.findAllByOwnerFirstNameAndOwnerLastName(owner.getFirstName(), owner.getLastName())).thenReturn(List.of(animal));
        List<Animal> animals = animalService.getAllByOwnerFirstNameAndOwnerLastName(owner.getFirstName(), owner.getLastName());

        assertNotNull(animals);
        assertEquals(1, animals.size());
        assertTrue(animals.contains(animal));
    }

    @Test
    public void givenType_whenFindAllByType_thenFindAnimals() {
        Owner owner = new Owner(1L, "John", "Smith", "johnsmith@yahoo.com",
                "MyPassword.12", "0745382312", List.of(animal));
        animal.setAnimalId(1L);
        animal.setOwner(owner);
        animal.setType(AnimalType.CAT);
        Animal animal1 = new Animal();
        animal1.setOwner(owner);
        animal1.setType(AnimalType.DOG);

        when(animalRepository.findAllByType(AnimalType.CAT)).thenReturn(List.of(animal));
        List<Animal> animals = animalService.getAllAnimalsByType(AnimalType.CAT);

        assertNotNull(animals);
        assertEquals(1, animals.size());
        assertTrue(animals.contains(animal));
    }

    @Test
    public void givenType_whenFindAllByAgeGraterThan_thenFindAnimals() {
        Owner owner = new Owner(1L, "John", "Smith", "johnsmith@yahoo.com",
                "MyPassword.12", "0745382312", List.of(animal));
        animal.setAnimalId(1L);
        animal.setOwner(owner);
        animal.setType(AnimalType.CAT);
        animal.setAge(1);
        Animal animal1 = new Animal();
        animal1.setOwner(owner);
        animal1.setType(AnimalType.CAT);
        animal1.setAge(5);

        when(animalRepository.findAllByAgeGreaterThan(4)).thenReturn(List.of(animal1));
        List<Animal> animals = animalService.getAllAnimalsByAgeGraterThan(4);

        assertNotNull(animals);
        assertEquals(1, animals.size());
        assertTrue(animals.contains(animal1));
    }

    @Test
    public void givenType_whenFindAllByName_thenFindAnimals() {
        Owner owner = new Owner(1L, "John", "Smith", "johnsmith@yahoo.com",
                "MyPassword.12", "0745382312", List.of(animal));
        animal.setAnimalId(1L);
        animal.setName("Mitzu");
        animal.setOwner(owner);
        animal.setType(AnimalType.CAT);
        animal.setAge(1);
        Animal animal1 = new Animal();
        animal1.setAnimalId(2L);
        animal1.setName("Mitzu");
        animal1.setOwner(owner);
        animal1.setType(AnimalType.CAT);
        animal1.setAge(5);
        Animal animal2 = new Animal();
        animal1.setAnimalId(3L);
        animal2.setName("Bue");
        animal2.setOwner(owner);
        animal2.setType(AnimalType.CAT);
        animal2.setAge(2);

        when(animalRepository.findAllByName("Mitzu")).thenReturn(List.of(animal, animal1));
        List<Animal> animals = animalService.getAllAnimalsByName("Mitzu");

        assertNotNull(animals);
        assertEquals(2, animals.size());
        assertTrue(animals.contains(animal1));
    }

    @Test
    public void givenValidAnimal_whenSaveAnimal_thenReturnSavedAnimal() {
        Owner owner = new Owner(1L, "John", "Smith", "johnsmith@yahoo.com",
                "MyPassword.12", "0745382312", new ArrayList<>());
        animal.setAnimalId(1L);
        animal.setName("Rex");
        animal.setOwner(owner);
        animal.setType(AnimalType.DOG);
        animal.setAge(2);

        when(animalRepository.save(animal)).thenReturn(animal);
        Animal savedAnimal = animalService.addAnimal(animal);

        assertNotNull(savedAnimal);
        assertEquals("Rex", savedAnimal.getName());
    }

    @Test
    public void givenInvalidAnimalOwner_whenSaveAnimal_thenThrowException() {
        Owner owner = new Owner(400L, "Mary", "Smith", "marysmith@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        animal.setAnimalId(1L);
        animal.setName("Rex");
        animal.setOwner(owner);
        animal.setType(AnimalType.DOG);
        animal.setAge(2);

        willThrow(new OwnerNotFoundException()).given(ownerService).getOwnerById(owner.getOwnerId());

        assertThrows(InvalidAnimalException.class, () -> {
            animalService.addAnimal(animal);
        });

        then(ownerService).should().getOwnerById(400L);
    }

    @Test
    public void givenNullAnimalType_whenSaveAnimal_thenThrowException() {
        Owner owner = new Owner(400L, "Mary", "Smith", "marysmith@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        animal.setAnimalId(400L);
        animal.setName("Rex");
        animal.setOwner(owner);
        animal.setAge(2);

        assertThrows(InvalidAnimalException.class, () -> {
            animalService.addAnimal(animal);
        });
    }

    @Test
    public void givenExistingAnimal_whenUpdateAnimal_thenReturnUpdatedAnimal() {
        Owner owner = new Owner(1L, "Mary", "Smith", "marysmith@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        animal.setAnimalId(1L);
        animal.setName("Rex");
        animal.setOwner(owner);
        animal.setType(AnimalType.DOG);
        animal.setAge(2);
        Animal animalUpdate = new Animal();
        animalUpdate.setAnimalId(1L);
        animalUpdate.setName("Haze");
        Animal animalToReturn = new Animal();
        animalToReturn.setAnimalId(animal.getAnimalId());
        animalToReturn.setName(animalUpdate.getName());
        animalToReturn.setOwner(animal.getOwner());
        animalToReturn.setType(animal.getType());

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(animalUpdate)).thenReturn(animalToReturn);

        Animal savedAnimal = animalService.updateAnimal(animalUpdate);

        assertNotNull(savedAnimal);
        assertEquals(owner, savedAnimal.getOwner());
        assertEquals("Haze", savedAnimal.getName());
    }

    @Test
    public void givenNonExistingAnimal_whenUpdateAnimal_thenThrowException() {
        Animal animalUpdate = new Animal();
        animalUpdate.setAnimalId(400L);
        animalUpdate.setName("Haze");

        when(animalRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(AnimalNotFoundException.class, () -> {
            animalService.updateAnimal(animalUpdate);
        });
    }

    @Test
    public void givenExistingAnimalWithNonExistingOwner_whenUpdateAnimal_thenThrowException() {
        Owner owner = new Owner(1L, "Mary", "Smith", "marysmith@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        animal.setAnimalId(1L);
        animal.setName("Rex");
        animal.setOwner(owner);
        animal.setType(AnimalType.DOG);
        animal.setAge(2);
        Owner ownerUpdate = new Owner(400L, "Miley", "Doe", "mileydoe@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        Animal animalUpdate = new Animal();
        animalUpdate.setAnimalId(1L);
        animalUpdate.setOwner(ownerUpdate);
        animalUpdate.setName("Haze");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        willThrow(new OwnerNotFoundException()).given(ownerService).getOwnerById(ownerUpdate.getOwnerId());

        assertThrows(InvalidAnimalException.class, () -> {
            animalService.updateAnimal(animalUpdate);
        });
    }

    @Test
    public void givenExistingAnimal_whenDeleteAnimal_thenSuccess() {
        Owner owner = new Owner(1L, "Mary", "Smith", "marysmith@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        animal.setAnimalId(1L);
        animal.setName("Rex");
        animal.setOwner(owner);
        animal.setType(AnimalType.DOG);
        animal.setAge(2);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).deleteById(animal.getAnimalId());

        animalService.deleteAnimalById(animal.getAnimalId());
        then(animalRepository).should().deleteById(1L);
    }

    @Test
    public void givenNonExistingAnimal_whenDeleteAnimal_thenThrowException() {
        Owner owner = new Owner(1L, "Mary", "Smith", "marysmith@gmail.ro",
                "Password@12", "0745382312", new ArrayList<>());
        animal.setAnimalId(400L);
        animal.setName("Mitzu");
        animal.setOwner(owner);
        animal.setType(AnimalType.CAT);
        animal.setAge(2);

        when(animalRepository.findById(400L)).thenReturn(Optional.empty());
        doNothing().when(animalRepository).deleteById(animal.getAnimalId());

        assertThrows(AnimalNotFoundException.class, () -> {
            animalService.deleteAnimalById(animal.getAnimalId());
        });
    }
}
