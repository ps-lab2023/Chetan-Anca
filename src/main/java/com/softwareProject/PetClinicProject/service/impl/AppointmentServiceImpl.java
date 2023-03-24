package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.AnimalNotFoundException;
import com.softwareProject.PetClinicProject.exception.AppointmentNotFoundException;
import com.softwareProject.PetClinicProject.exception.DoctorNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidAppointmentException;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.repository.AppointmentRepository;
import com.softwareProject.PetClinicProject.service.AnimalService;
import com.softwareProject.PetClinicProject.service.AppointmentService;
import com.softwareProject.PetClinicProject.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AnimalService animalService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorService doctorService, AnimalService animalService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    @Override
    public Optional<Appointment> findById(long id) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException("Appointment with id " + id + " not found");
        }
        return appointment;
    }

    @Override
    public Optional<Appointment> findByDoctorAndTime(Doctor doctor, LocalDateTime time) throws AppointmentNotFoundException{
        Optional<Appointment> appointment = appointmentRepository.findByDoctorAndTime(doctor, time);
        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException("Appointment with id not found");
        }
        return appointment;
    }

    @Override
    public List<Appointment> findAllByAnimal(Animal animal) {
        return appointmentRepository.findAllByAnimal(animal);
    }

    @Override
    public List<Appointment> findAllByDoctor(Doctor doctor) {
        return appointmentRepository.findAllByDoctor(doctor);
    }

    @Override
    public List<Appointment> findAllByTime(LocalTime time) {
        return appointmentRepository.findAllByTime(time);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> addAppointment(Appointment appointment) throws InvalidAppointmentException {
        try {
            doctorService.findById(appointment.getDoctor().getDoctorId());
        } catch (DoctorNotFoundException exp) {
            throw new InvalidAppointmentException("Doctor not found");
        }
        try {
            animalService.findById(appointment.getAnimal().getAnimalId());
        } catch (AnimalNotFoundException exp) {
            throw new InvalidAppointmentException("Animal not found");
        }
        if (appointment.getTime() == null) {
            throw new InvalidAppointmentException("Appointment must have a time");
        }
        Optional<Appointment> foundAppointment = appointmentRepository.findByDoctorAndTime(appointment.getDoctor(), appointment.getTime());
        if (!foundAppointment.isPresent()) {
            appointment.getDoctor().getAppointments().add(appointment);
            appointment.getAnimal().getAppointments().add(appointment);
            appointmentRepository.save(appointment);
        } else {
            throw new InvalidAppointmentException("Appointment at this time already exists");
        }
        return Optional.of(appointment);
    }

    @Override
    public Optional<Appointment> updateAppointmentTime(Appointment appointment) throws InvalidAppointmentException, AppointmentNotFoundException {
        Optional<Appointment> appointmentToUpdate = appointmentRepository.findById(appointment.getAppointmentId());
        if (appointmentToUpdate.isPresent()) {
            if (appointment.getTime() != null) {
                appointmentToUpdate.get().setTime(appointment.getTime());
            }
            appointmentRepository.save(appointmentToUpdate.get());
        } else {
            throw new AppointmentNotFoundException("Appointment to update not found");
        }
        return appointmentToUpdate;
    }

    @Override
    public void deleteById(long id) throws AppointmentNotFoundException {
        Optional<Appointment> appointmentToDelete = appointmentRepository.findById(id);
        if (appointmentToDelete.isPresent()) {
            appointmentRepository.deleteById(id);
        } else {
            throw new AppointmentNotFoundException("Appointment to delete not found");
        }
    }
}
