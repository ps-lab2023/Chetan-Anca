package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidUserException;
import com.softwareProject.PetClinicProject.exception.UserNotFoundException;
import com.softwareProject.PetClinicProject.exception.WrongDetailsException;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.repository.UserRepository;
import com.softwareProject.PetClinicProject.service.impl.UserServiceImpl;
import com.softwareProject.PetClinicProject.validator.UserDetailsValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsValidator userDetailsValidator;
    private UserService userService;

    @Before
    public void setUp() {
        initMocks(this);
        userService = new UserServiceImpl(userRepository, userDetailsValidator);
    }

    @Test
    public void givenExistingUserId_whenFindById_thenFindUser() {
        User user = createValidUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    public void givenNonExistingUserId_whenFindById_thenThrowException() {
        User user = createNonExistingUser();

        when(userRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(user.getId());
        });
    }

    @Test
    public void givenExistingEmailAndPassword_whenFindByEmailAndPassword_thenFindUser() {
        User user = createValidUser();

        when(userRepository.findByEmailAndPassword("johnsmith@yahoo.com", "MyPassword.12")).thenReturn(Optional.of(user));
        User foundUser = userService.findByEmailAndPassword("johnsmith@yahoo.com", "MyPassword.12");

        assertNotNull(foundUser);
        assertEquals("johnsmith@yahoo.com", foundUser.getEmail());
        assertEquals("MyPassword.12", foundUser.getPassword());
    }

    @Test
    public void givenNonExistingEmailAndPassword_whenFindByEmailAndPassword_thenThrowException() {
        User user = createNonExistingUser();

        when(userRepository.findByEmailAndPassword("marysmith@gmail.ro", "Password@12")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        });
    }

    @Test
    public void givenNonUserType_whenFindAllByUserType_thenReturnUsers() {
        User user = createValidUser();
        User user2 = new User(2L, "marypoppins@gamil.com", "Password45#", UserType.DOCTOR);
        User user3 = new User(3L, "dragusandre@gmail.com", "MyPassword%12", UserType.OWNER);

        when(userRepository.findAllByUserType(UserType.DOCTOR)).thenReturn(List.of(user, user2));
        List<User> users = userService.findAllByUserType(UserType.DOCTOR);

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user2));
    }

    @Test
    public void givenValidUser_whenSaveUser_thenReturnSavedUser() {
        User user = createValidUser();

        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("johnsmith@yahoo.com", savedUser.getEmail());
    }

    @Test
    public void givenInvalidUser_whenSaveUser_thenThrowException() {
        User user = createInvalidUser();
        willThrow(new WrongDetailsException()).given(userDetailsValidator).validateUserDetails(user);

        assertThrows(InvalidUserException.class, () -> {
            userService.addUser(user);
        });
    }

    @Test
    public void givenExistingUser_whenUpdateUser_thenReturnUpdatedUser() {
        User user = createValidUser();
        User userUpdate = new User();
        userUpdate.setId(1L);
        userUpdate.setEmail("johnsmith12@yahoo.com");
        User userToReturn = new User();
        userToReturn.setId(1L);
        userToReturn.setEmail(userUpdate.getEmail());
        userToReturn.setPassword(user.getPassword());
        userToReturn.setUserType(user.getUserType());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(userUpdate)).thenReturn(userToReturn);
        User updatedUser = userService.updateUser(userUpdate);

        assertNotNull(updatedUser);
        assertEquals("johnsmith12@yahoo.com", updatedUser.getEmail());
        assertEquals("MyPassword.12", updatedUser.getPassword());
    }

    @Test
    public void givenNonExistingUser_whenUpdateUser_thenThrowException() {
        User user = createNonExistingUser();
        User userUpdate = new User();
        userUpdate.setId(user.getId());

        when(userRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userUpdate);
        });
    }

    @Test
    public void givenUserWithInvalidUserDetails_whenUpdateUser_thenThrowException() {
        User user = createValidUser();
        User userUpdate = new User();
        userUpdate.setId(1L);
        userUpdate.setEmail("john.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        willThrow(new WrongDetailsException()).given(userDetailsValidator).validateUserDetails(user);

        assertThrows(InvalidUserException.class, () -> {
            userService.updateUser(userUpdate);
        });
    }

    @Test
    public void givenExistingUser_whenDeleteUser_thenSuccess() {
        User user = createValidUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        userService.deleteById(user.getId());
        then(userRepository).should().deleteById(1L);
    }

    @Test
    public void givenNonExistingUser_whenDeleteUser_thenThrowException() {
        User user = createNonExistingUser();

        when(userRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteById(user.getId());
        });
    }

    private User createValidUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("johnsmith@yahoo.com");
        user.setPassword("MyPassword.12");
        user.setUserType(UserType.DOCTOR);
        return user;
    }

    private User createNonExistingUser() {
        User user = new User();
        user.setId(400L);
        user.setEmail("marysmith@gamil.ro");
        user.setPassword("Password@12");
        user.setUserType(UserType.DOCTOR);
        return user;
    }

    private User createInvalidUser() {
        User user = new User();
        user.setId(400L);
        user.setEmail("marysmith.ro");
        user.setPassword("Password@12");
        user.setUserType(UserType.DOCTOR);
        return user;
    }
}
