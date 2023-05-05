package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.DoctorDto;
import com.softwareProject.PetClinicProject.dto.OwnerDto;
import com.softwareProject.PetClinicProject.dto.UserDto;
import com.softwareProject.PetClinicProject.security.AuthenticationService;
import com.softwareProject.PetClinicProject.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final EmailService emailService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.registerAdmin(userDto));
    }

    @PostMapping("/registerOwner")
    public ResponseEntity<AuthenticationResponse> registerOwner(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(authenticationService.registerOwner(ownerDto));
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<AuthenticationResponse> registerDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(authenticationService.registerDoctor(doctorDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/verify")
    public ResponseEntity verify(@RequestParam String email) {
        Random random = new Random();
        int code = random.nextInt(100000, 999999);
        emailService.sendSimpleMessage(email, "Authentication code", "Your unique code is: " + code);
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

}
