package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.DoctorDto;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/findById")
    public ResponseEntity getDoctorById(@RequestParam long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity getDoctorByEmail(@RequestParam String email) {
        Doctor doctor = doctorService.getDoctorByEmail(email);
        DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
    }

    @GetMapping("/findByFirstName")
    public ResponseEntity getDoctorsByFirstName(@RequestParam String firstName) {
        List<Doctor> doctors = doctorService.getAllDoctorsByFirstName(firstName);
        List<DoctorDto> doctorsDto = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(doctorsDto);
    }

    @GetMapping("/findByLastName")
    public ResponseEntity getDoctorsByLastName(@RequestParam String lastName) {
        List<Doctor> doctors = doctorService.getAllDoctorsByLastName(lastName);
        List<DoctorDto> doctorsDto = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(doctorsDto);
    }


    @GetMapping("/findByFullName")
    public ResponseEntity getDoctorByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        Doctor doctor = doctorService.getDoctorByFirstNameAndLastName(firstName, lastName);
        DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
    }

    @GetMapping("/findByCredentials")
    public ResponseEntity getDoctorByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        Doctor doctor = doctorService.getDoctorByEmailAndPassword(email, password);
        DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
    }

    @GetMapping()
    public ResponseEntity getAllDoctors() {
        List<Doctor> doctors = doctorService.findAllDoctors();
        List<DoctorDto> doctorsDto = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(doctorsDto);
    }

    @PostMapping("/add")
    public ResponseEntity addDoctor(@RequestBody DoctorDto doctorDto) {
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        Doctor doctorReturned = doctorService.addDoctor(doctor);
        DoctorDto doctorDtoReturned = modelMapper.map(doctorReturned, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorDtoReturned);
    }

    @PutMapping("/update")
    public ResponseEntity updateDoctor(@RequestBody DoctorDto doctorDto) {
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        Doctor doctorReturned = doctorService.updateDoctor(doctor);
        DoctorDto doctorDtoReturned = modelMapper.map(doctorReturned, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(doctorDtoReturned);
    }

    @DeleteMapping("/delete")
    public void deleteDoctor(@RequestParam long id) {
        doctorService.deleteDoctorById(id);
    }
}
