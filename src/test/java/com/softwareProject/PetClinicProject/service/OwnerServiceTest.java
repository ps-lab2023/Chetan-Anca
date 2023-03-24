package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.*;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.repository.OwnerRepository;
import com.softwareProject.PetClinicProject.service.impl.OwnerServiceImpl;
import com.softwareProject.PetClinicProject.validator.OwnerDetailsValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OwnerServiceTest {
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private OwnerDetailsValidator ownerDetailsValidator;
    @Mock
    private UserService userService;
    private OwnerService ownerService;

    @Before
    public void setUp() {
        initMocks(this);
        ownerService = new OwnerServiceImpl(ownerRepository, ownerDetailsValidator, userService);
    }

    @Test
    public void givenExistingOwnerId_whenFindById_thenFindOwner() {
        Owner owner = createValidOwner();

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Optional<Owner> foundOwner = ownerService.findById(owner.getOwnerId());

        assertNotNull(foundOwner);
        assertEquals(1L, foundOwner.get().getOwnerId());
    }

    @Test
    public void givenNonExistingOwnerId_whenFindById_thenThrowException() {
        Owner owner = createNonExistingOwner();

        when(ownerRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findById(owner.getOwnerId());
        });
    }

    @Test
    public void givenExistingEmailAndPassword_whenFindByEmailAndPassword_thenFindOwner() {
        Owner owner = createValidOwner();

        when(ownerRepository.findByEmailAndPassword("johnsmith@yahoo.com", "MyPassword.12")).thenReturn(Optional.of(owner));
        Optional<Owner> foundOwner = ownerService.findByEmailAndPassword("johnsmith@yahoo.com", "MyPassword.12");

        assertNotNull(foundOwner);
        assertEquals("johnsmith@yahoo.com", foundOwner.get().getEmail());
        assertEquals("MyPassword.12", foundOwner.get().getPassword());
    }

    @Test
    public void givenNonExistingEmailAndPassword_whenFindByEmailAndPassword_thenThrowException() {
        Owner owner = createNonExistingOwner();

        when(ownerRepository.findByEmailAndPassword("marysmith@gmail.ro", "Password@12")).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findByEmailAndPassword(owner.getEmail(), owner.getPassword());
        });
    }

    @Test
    public void givenExistingFirstNameAndLastName_whenFindByFirstNameAndLastName_thenFindOwner() {
        Owner owner = createValidOwner();

        when(ownerRepository.findByFirstNameAndLastName("John", "Smith")).thenReturn(Optional.of(owner));
        Optional<Owner> foundOwner = ownerRepository.findByFirstNameAndLastName("John", "Smith");

        assertNotNull(foundOwner);
        assertEquals("John", foundOwner.get().getFirstName());
        assertEquals("Smith", foundOwner.get().getLastName());
    }

    @Test
    public void givenNonExistingFirstNameAndLastName_whenFindByFirstNameAndLastName_thenFindOwner() {
        Owner owner = createNonExistingOwner();

        when(ownerRepository.findByFirstNameAndLastName("Mary", "Smith")).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findByFirstNameAndLastName(owner.getFirstName(), owner.getLastName());
        });
    }

    @Test
    public void givenValidOwner_whenSaveOwner_thenReturnSavedOwner() {
        Owner owner = createValidOwner();
        User user = new User(1L, owner.getEmail(), owner.getPassword(), UserType.OWNER);

        when(ownerRepository.save(owner)).thenReturn(owner);
        when(userService.addUser(user)).thenReturn(Optional.of(user));
        Optional<Owner> savedOwner = ownerService.addOwner(owner);

        assertTrue(savedOwner.isPresent());
        assertEquals("johnsmith@yahoo.com", savedOwner.get().getEmail());
        then(userService).should().addUser(user);
    }

    @Test
    public void givenInvalidOwner_whenSaveOwner_thenThrowException() {
        Owner owner = createInvalidOwner();
        willThrow(new WrongDetailsException()).given(ownerDetailsValidator).validateOwnerDetails(owner);

        assertThrows(InvalidOwnerException.class, () -> {
            ownerService.addOwner(owner);
        });
    }

    @Test
    public void givenExistingOwner_whenUpdateOwner_thenReturnUpdatedOwner() {
        Owner owner = createValidOwner();
        Owner ownerUpdate = new Owner();
        ownerUpdate.setOwnerId(1L);
        ownerUpdate.setEmail("johnsmith12@yahoo.com");
        Owner ownerToReturn = new Owner();
        ownerToReturn.setOwnerId(1L);
        ownerToReturn.setFirstName(owner.getFirstName());
        ownerToReturn.setLastName(owner.getLastName());
        ownerToReturn.setEmail(ownerUpdate.getEmail());
        ownerToReturn.setPassword(owner.getPassword());
        ownerToReturn.setPhoneNumber(owner.getPhoneNumber());
        User user = new User(ownerToReturn.getOwnerId(), ownerToReturn.getEmail(), ownerToReturn.getPassword(), UserType.OWNER);

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerRepository.save(ownerUpdate)).thenReturn(ownerToReturn);
        when(userService.addUser(user)).thenReturn(Optional.of(user));
        Optional<Owner> updatedOwner = ownerService.updateOwner(ownerUpdate);

        assertNotNull(updatedOwner);
        assertEquals("johnsmith12@yahoo.com", updatedOwner.get().getEmail());
        assertEquals("0745382312", updatedOwner.get().getPhoneNumber());
        then(userService).should().addUser(user);
    }

    @Test
    public void givenNonExistingOwner_whenUpdateOwner_thenThrowException() {
        Owner ownerUpdate = new Owner();
        ownerUpdate.setOwnerId(400L);

        when(ownerRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.updateOwner(ownerUpdate);
        });
    }

    @Test
    public void givenOwnerWithInvalidDetails_whenUpdateUser_thenThrowException() {
        Owner owner = createValidOwner();
        Owner ownerUpdate = new Owner();
        ownerUpdate.setOwnerId(1L);
        ownerUpdate.setEmail("johnsmith12");

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        willThrow(new WrongDetailsException()).given(ownerDetailsValidator).validateOwnerDetails(owner);

        assertThrows(InvalidOwnerException.class, () -> {
            ownerService.updateOwner(ownerUpdate);
        });
    }

    @Test
    public void givenExistingOwner_whenDeleteOwner_thenSuccess() {
        Owner owner = createValidOwner();

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        doNothing().when(ownerRepository).deleteById(owner.getOwnerId());

        ownerService.deleteById(owner.getOwnerId());
        then(ownerRepository).should().deleteById(1L);
    }

    @Test
    public void givenNonExistingUser_whenDeleteUser_thenThrowException() {
        Owner owner = createNonExistingOwner();

        when(ownerRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.deleteById(owner.getOwnerId());
        });
    }

    private Owner createValidOwner() {
        Owner owner = new Owner();
        owner.setOwnerId(1L);
        owner.setFirstName("John");
        owner.setLastName("Smith");
        owner.setEmail("johnsmith@yahoo.com");
        owner.setPassword("MyPassword.12");
        owner.setPhoneNumber("0745382312");
        return owner;
    }

    private Owner createNonExistingOwner() {
        Owner owner = new Owner();
        owner.setOwnerId(400L);
        owner.setFirstName("Mary");
        owner.setLastName("Smith");
        owner.setEmail("marysmith@gmail.ro");
        owner.setPassword("Password@12");
        owner.setPhoneNumber("0745382312");
        return owner;
    }

    private Owner createInvalidOwner() {
        Owner owner = new Owner();
        owner.setOwnerId(400L);
        owner.setFirstName("Mary");
        owner.setLastName("Smith");
        owner.setEmail("marysmith");
        owner.setPassword("Password@12");
        owner.setPhoneNumber("0745382312");
        return owner;
    }
}
