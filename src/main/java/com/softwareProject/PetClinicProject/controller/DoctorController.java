package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/findById")
    public ResponseEntity findDoctorById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findById(id));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity findDoctorByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findByEmail(email));
    }


    @GetMapping("/findByFullName")
    public ResponseEntity findDoctorByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("/findByCredentials")
    public ResponseEntity findDoctorByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findByEmailAndPassword(email, password));
    }

    @GetMapping()
    public ResponseEntity findAllDoctors() {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity addDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.addDoctor(doctor));
    }

    @PutMapping("/update")
    public ResponseEntity updateDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.updateDoctor(doctor));
    }

    @DeleteMapping("/delete")
    public void deleteDoctor(@RequestParam long id) {
        doctorService.deleteById(id);
    }
}
