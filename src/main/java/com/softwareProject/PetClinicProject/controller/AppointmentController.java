package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/findById")
    public ResponseEntity findAppointmentById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findById(id));
    }

    @GetMapping("/findByDoctorAndTime")
    public ResponseEntity findAllAppointmentsByDoctorAndTime(@RequestBody Doctor doctor, @RequestParam LocalDateTime time) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findByDoctorAndTime(doctor, time));
    }

    @GetMapping("/findByDoctor")
    public ResponseEntity findAllAppointmentsByDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findAllByDoctor(doctor));
    }

    @GetMapping("/findByAnimal")
    public ResponseEntity findAllAppointmentsByAnimal(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findAllByAnimal(animal));
    }

    @GetMapping("/findByTime")
    public ResponseEntity findAllAppointmentsByTime(@RequestParam LocalDateTime time) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findAllByTime(time));
    }

    @GetMapping()
    public ResponseEntity findAllAppointments() {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity addAppointment(@RequestBody Appointment appointment) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.addAppointment(appointment));
    }

    @PutMapping("/update")
    public ResponseEntity updateAppointment(@RequestBody Appointment appointment) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.updateAppointmentTime(appointment));
    }

    @DeleteMapping("/delete")
    public void deleteAppointment(@RequestParam long id) {
        appointmentService.deleteById(id);
    }
}
