package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.InvalidUserException;
import com.softwareProject.PetClinicProject.exception.UserNotFoundException;
import com.softwareProject.PetClinicProject.service.UserService;
import com.softwareProject.PetClinicProject.exception.WrongDetailsException;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.repository.UserRepository;
import com.softwareProject.PetClinicProject.validator.UserDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsValidator userDetailsValidator;

    public UserServiceImpl(UserRepository userRepository, UserDetailsValidator userDetailsValidator) {
        this.userRepository = userRepository;
        this.userDetailsValidator = userDetailsValidator;
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user.get();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with email " + email + " and password " + password + " not found");
        }
        return user.get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByUserType(UserType userType) {
        return userRepository.findAllByUserType(userType);
    }

    @Override
    public User addUser(User user) throws InvalidUserException {
        try {
            userDetailsValidator.validateUserDetails(user);
            Optional<User> userToAdd = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (!userToAdd.isPresent()) {
                userRepository.save(user);
            }
        } catch (WrongDetailsException exp) {
            throw new InvalidUserException(exp.getMessage());
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws InvalidUserException, UserNotFoundException {
        Optional<User> userToUpdate = userRepository.findById(user.getId());
        if (userToUpdate.isPresent()) {
            User finalUser = createUser(user, userToUpdate.get());
            try {
                userDetailsValidator.validateUserDetails(finalUser);
            } catch (WrongDetailsException exp) {
                throw new InvalidUserException(exp.getMessage());
            }
            userRepository.save(finalUser);
        } else {
            throw new UserNotFoundException("User to update not found");
        }
        return userToUpdate.get();
    }

    @Override
    public void deleteById(long id) throws UserNotFoundException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User to delete not found");
        }
    }

    private User createUser(User user, User userToUpdate) {
        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }
        return userToUpdate;
    }
}
