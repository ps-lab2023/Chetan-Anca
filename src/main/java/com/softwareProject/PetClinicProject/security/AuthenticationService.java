package com.softwareProject.PetClinicProject.security;

import com.softwareProject.PetClinicProject.controller.*;
import com.softwareProject.PetClinicProject.dto.DoctorDto;
import com.softwareProject.PetClinicProject.dto.OwnerDto;
import com.softwareProject.PetClinicProject.dto.UserDto;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.service.DoctorService;
import com.softwareProject.PetClinicProject.service.OwnerService;
import com.softwareProject.PetClinicProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final OwnerService ownerService;
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerAdmin(UserDto userDto) {
        var user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userType(UserType.ADMIN)
                .build();
        userService.addUser(user);
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse registerOwner(OwnerDto ownerDto) {
        var owner = Owner.builder()
                .firstName(ownerDto.getFirstName())
                .lastName(ownerDto.getLastName())
                .email(ownerDto.getEmail())
                .password(passwordEncoder.encode(ownerDto.getPassword()))
                .phoneNumber(ownerDto.getPhoneNumber())
                .build();
        ownerService.addOwner(owner);
        var token = jwtService.generateToken(owner);

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse registerDoctor(DoctorDto doctorDto) {
        var doctor = Doctor.builder()
                .firstName(doctorDto.getFirstName())
                .lastName(doctorDto.getLastName())
                .email(doctorDto.getEmail())
                .password(passwordEncoder.encode(doctorDto.getPassword()))
                .phoneNumber(doctorDto.getPhoneNumber())
                .startScheduleTime(doctorDto.getStartScheduleTime())
                .endScheduleTime(doctorDto.getEndScheduleTime())
                .build();
        doctorService.addDoctor(doctor);
        var token = jwtService.generateToken(doctor);

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
        var user = userService.getUserByEmail(request.getEmail());
        user.setLogged(true);
        user.setLastLoggedIn(LocalDateTime.now());
        userService.updateUser(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }
}
