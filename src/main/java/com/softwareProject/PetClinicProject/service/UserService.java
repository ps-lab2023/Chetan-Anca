package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.DoctorNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidUserException;
import com.softwareProject.PetClinicProject.exception.UserNotFoundException;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    User getUserById(long id) throws UserNotFoundException;

    User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    List<User> getAllUsers();

    List<User> getAllUsersByUserType(UserType userType);

    User addUser(User user) throws InvalidUserException;

    User updateUser(User user) throws UserNotFoundException, InvalidUserException;

    void deleteUserById(long id) throws UserNotFoundException;
}
