package com.softwareProject.PetClinicProject.controller;


import com.softwareProject.PetClinicProject.dto.UserDto;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.service.DoctorService;
import com.softwareProject.PetClinicProject.service.EmailService;
import com.softwareProject.PetClinicProject.service.OwnerService;
import com.softwareProject.PetClinicProject.service.UserService;
import com.softwareProject.PetClinicProject.utils.PasswordUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public EmailService emailService;

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

    @PutMapping("/forgotPassword")
    public ResponseEntity forgotUserPassword(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        String password = PasswordUtil.generateUniquePassword();
        if (user.getUserType().equals(UserType.OWNER)) {
            ownerService.updatePassword(user.getId(), password);
        } else if (user.getUserType().equals(UserType.DOCTOR)) {
            doctorService.updatePassword(user.getId(), password);
        }
        emailService.sendSimpleMessage(email, "Authentication details", "Your new password is: " + password + "\n" + "You can change it when you authenticate in your account!");
        return ResponseEntity.status(HttpStatus.OK).body(password);
    }

    @PutMapping("/logout")
    public ResponseEntity logout(@RequestParam long id) {
        User user = userService.getUserById(id);
        user.setLogged(false);
        user.setLastLoggedOut(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user));
    }
}
