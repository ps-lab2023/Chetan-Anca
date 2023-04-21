package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.UserDto;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/findById")
    public ResponseEntity getUserById(@RequestParam long id) {
        User user = userService.getUserById(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/findByCredentials")
    public ResponseEntity getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        User user = userService.getUserByEmailAndPassword(email, password);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/findAllByUserType")
    public ResponseEntity getAllUsersByUserType(@RequestParam UserType userType) {
        List<User> users = userService.getAllUsersByUserType(userType);
        List<UserDto> usersDto = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(usersDto);
    }

    @GetMapping()
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> usersDto = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(usersDto);
    }
}
