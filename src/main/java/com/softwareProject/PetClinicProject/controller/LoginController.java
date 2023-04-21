package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.UserDto;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.service.EmailService;
import com.softwareProject.PetClinicProject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;

    @PostMapping()
    public ResponseEntity login(@RequestBody UserDto userDto) {
        User userFound = userService.getUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        UserDto userFoundDto = modelMapper.map(userFound, UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userFoundDto);
    }

    @GetMapping("/verify")
    public ResponseEntity verify(@RequestParam String email) {
        Random random = new Random();
        int code = random.nextInt(100000, 999999);
        emailService.sendSimpleMessage(email, Integer.toString(code));
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }
}
