package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.AppointmentDto;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.MedicalFacility;
import com.softwareProject.PetClinicProject.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/findById")
    public ResponseEntity getAppointmentById(@RequestParam long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDto);
    }

    @GetMapping("/findByDoctorAndDate")
    public ResponseEntity getAppointmentByDoctorAndTime(@RequestParam String firstName, @RequestParam String lastName, @RequestParam LocalDateTime date) {
        Appointment appointment = appointmentService.getByDoctorFirstNameAndDoctorLastNameAndDate(firstName, lastName, date);
        AppointmentDto appointmentsDto = modelMapper.map(appointment, AppointmentDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @GetMapping("/findByDoctorId")
    public ResponseEntity getAllAppointmentsByDoctorId(long id) {
        List<Appointment> appointments = appointmentService.getAllByDoctorId(id);
        List<AppointmentDto> appointmentsDto = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @GetMapping("/findByDoctorName")
    public ResponseEntity getAllAppointmentsByDoctorName(@RequestParam String firstName, @RequestParam String lastName) {
        List<Appointment> appointments = appointmentService.getAllByDoctorFirstNameAndDoctorLastName(firstName, lastName);
        List<AppointmentDto> appointmentsDto = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @GetMapping("/findByAnimal")
    public ResponseEntity getAllAppointmentsByAnimal(@RequestParam String name) {
        List<Appointment> appointments = appointmentService.getAllByAnimalName(name);
        List<AppointmentDto> appointmentsDto = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @GetMapping("/findByOwner")
    public ResponseEntity getAllByAnimalOwnerId(@RequestParam long id) {
        List<Appointment> appointments = appointmentService.getAllByAnimalOwnerId(id);
        List<AppointmentDto> appointmentsDto = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @GetMapping("/findByDate")
    public ResponseEntity getAllAppointmentsByDate(@RequestParam LocalDateTime date) {
        List<Appointment> appointments = appointmentService.getAllAppointmentsByTime(date);
        List<AppointmentDto> appointmentsDto = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @GetMapping()
    public ResponseEntity getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAppointmentsAll();
        List<AppointmentDto> appointmentsDto = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsDto);
    }

    @PostMapping("/add")
    public ResponseEntity addAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        List<MedicalFacility> medicalFacilities = appointmentDto.getMedicalFacilities()
                .stream()
                .map(medicalFacilityDto -> modelMapper.map(medicalFacilityDto, MedicalFacility.class))
                .collect(Collectors.toList());
        appointment.setMedicalFacilities(medicalFacilities);
        Appointment appointmentReturned = appointmentService.addAppointment(appointment);
        AppointmentDto appointmentDtoReturned = modelMapper.map(appointmentReturned, AppointmentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentDtoReturned);
    }

    @PutMapping("/update")
    public ResponseEntity updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        List<MedicalFacility> medicalFacilities = appointmentDto.getMedicalFacilities()
                .stream()
                .map(medicalFacilityDto -> modelMapper.map(medicalFacilityDto, MedicalFacility.class))
                .collect(Collectors.toList());
        appointment.setMedicalFacilities(medicalFacilities);
        Appointment appointmentReturned = appointmentService.updateAppointment(appointment);
        AppointmentDto appointmentDtoReturned = modelMapper.map(appointmentReturned, AppointmentDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDtoReturned);
    }

    @DeleteMapping("/delete")
    public void deleteAppointment(@RequestParam long id) {
        appointmentService.deleteAppointmentById(id);
    }
}
