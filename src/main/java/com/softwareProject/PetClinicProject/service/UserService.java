package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidUserException;
import com.softwareProject.PetClinicProject.exception.UserNotFoundException;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    User findById(long id) throws UserNotFoundException;

    User findByEmailAndPassword(String email, String password) throws UserNotFoundException;

    List<User> findAll();

    List<User> findAllByUserType(UserType userType);

    User addUser(User user) throws InvalidUserException;

    User updateUser(User user) throws UserNotFoundException, InvalidUserException;

    void  deleteById(long id) throws UserNotFoundException;
}
