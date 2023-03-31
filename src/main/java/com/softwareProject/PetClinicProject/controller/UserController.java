package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findById")
    public ResponseEntity findUserById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity findUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByEmailAndPassword(email, password));
    }

    @GetMapping("/findAllByUserType")
    public ResponseEntity findAllUsersByUserType() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping()
    public ResponseEntity findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
}
