package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidUserException;
import com.softwareProject.PetClinicProject.exception.UserNotFoundException;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {
    Optional<User> findById(long id) throws UserNotFoundException;

    Optional<User> findByEmailAndPassword(String email, String password) throws UserNotFoundException;

    List<User> findAll();

    List<User> findAllByUserType(UserType userType);

    Optional<User> addUser(User user) throws InvalidUserException;

    Optional<User> updateUser(User user) throws UserNotFoundException, InvalidUserException;

    void  deleteById(long id) throws UserNotFoundException;
}
