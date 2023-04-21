package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.*;
import com.softwareProject.PetClinicProject.model.Animal;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.MedicalFacility;
import com.softwareProject.PetClinicProject.repository.AppointmentRepository;
import com.softwareProject.PetClinicProject.service.AnimalService;
import com.softwareProject.PetClinicProject.service.AppointmentService;
import com.softwareProject.PetClinicProject.service.DoctorService;
import com.softwareProject.PetClinicProject.service.MedicalFacilityService;
import com.softwareProject.PetClinicProject.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private MedicalFacilityService medicalFacilityService;

    @Autowired
    private DateValidator dateValidator;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorService doctorService, AnimalService animalService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    @Override
    public Appointment getAppointmentById(long id) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException("Appointment with id " + id + " not found");
        }
        return appointment.get();
    }


    @Override
    public Appointment getByDoctorFirstNameAndDoctorLastNameAndDate(String firstName, String lastName, LocalDateTime date) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findByDoctorFirstNameAndDoctorLastNameAndDate(firstName, lastName, date);
        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException("Appointment at this doctor and at this date not found");
        }
        return appointment.get();
    }

    @Override
    public List<Appointment> getAllByAnimalName(String name) {
        return appointmentRepository.findAllByAnimalName(name);
    }


    @Override
    public List<Appointment> getAllByDoctorFirstNameAndDoctorLastName(String firstName, String lastName) {
        return appointmentRepository.findAllByDoctorFirstNameAndDoctorLastName(firstName, lastName);
    }

    @Override
    public List<Appointment> getAllAppointmentsByTime(LocalDateTime date) {
        return appointmentRepository.findAllByDate(date);
    }

    @Override
    public List<Appointment> getAllByAnimalOwnerId(long id) {
        return appointmentRepository.findAllByAnimalOwnerOwnerId(id);
    }

    @Override
    public List<Appointment> getAllByDoctorId(long id) {
        return appointmentRepository.findAllByDoctorDoctorId(id);
    }

    @Override
    public List<Appointment> getAppointmentsAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment addAppointment(Appointment appointment) throws InvalidAppointmentException {
        Doctor doctor;
        Animal animal;
        try {
            doctor = doctorService.getDoctorById(appointment.getDoctor().getDoctorId());
        } catch (DoctorNotFoundException exp) {
            throw new InvalidAppointmentException("Doctor not found");
        }
        try {
            animal = animalService.getAnimalById(appointment.getAnimal().getAnimalId());
        } catch (AnimalNotFoundException exp) {
            throw new InvalidAppointmentException("Animal not found");
        }
        if (appointment.getDate() == null) {
            throw new InvalidAppointmentException("Appointment must have a date");
        }
        try {
            dateValidator.validateDate(appointment.getDate());
        } catch (InvalidDate exp) {
            throw new InvalidAppointmentException(exp.getMessage());
        }
        if (!(appointment.getDate().getHour() >= doctor.getStartScheduleTime().getHour() &&
                appointment.getDate().getHour() < doctor.getEndScheduleTime().getHour())) {
            throw new InvalidAppointmentException("Appointment date is out of doctor's program");
        }
        if (appointment.getMedicalFacilities().isEmpty()) {
            throw new InvalidAppointmentException("Appointment must have a least a medical service");
        }
        Optional<Appointment> foundAppointment = appointmentRepository.findByDoctorFirstNameAndDoctorLastNameAndDate(appointment.getDoctor().getFirstName(), appointment.getDoctor().getLastName(), appointment.getDate());
        if (!foundAppointment.isPresent()) {
            List<MedicalFacility> facilities = new ArrayList<>();
            for (MedicalFacility medicalFacility : appointment.getMedicalFacilities()) {
                try {
                    medicalFacilityService.getMedicalFacilityById(medicalFacility.getId());
                } catch (MedicalFacilityNotFoundException exp) {
                    throw new InvalidAppointmentException("Invalid medical facility!");
                }
                facilities.add(medicalFacilityService.getMedicalFacilityById(medicalFacility.getId()));
            }
            appointment.setMedicalFacilities(facilities);
            appointment.setDoctor(doctor);
            appointment.setAnimal(animal);
            appointmentRepository.save(appointment);
        } else {
            throw new InvalidAppointmentException("Appointment at this time already exists");
        }
        return appointment;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) throws InvalidAppointmentException, AppointmentNotFoundException {
        Optional<Appointment> appointmentToUpdate = appointmentRepository.findById(appointment.getAppointmentId());
        if (appointmentToUpdate.isPresent()) {
            if (appointment.getDate() != appointmentToUpdate.get().getDate()) {
                appointmentToUpdate.get().setDate(appointment.getDate());
            }
            if (appointmentToUpdate.get().getMedicalFacilities().contains(appointment.getMedicalFacilities())) {
                appointmentToUpdate.get().setMedicalFacilities(appointment.getMedicalFacilities());
            }
            if (appointmentToUpdate.get().getMedicalFacilities().size() != appointment.getMedicalFacilities().size()) {
                appointmentToUpdate.get().setMedicalFacilities(appointment.getMedicalFacilities());
            }
            appointmentRepository.save(appointmentToUpdate.get());
        } else {
            throw new AppointmentNotFoundException("Appointment to update not found");
        }
        return appointmentToUpdate.get();
    }

    @Override
    public void deleteAppointmentById(long id) throws AppointmentNotFoundException {
        Optional<Appointment> appointmentToDelete = appointmentRepository.findById(id);
        if (appointmentToDelete.isPresent()) {
            appointmentRepository.deleteById(id);
        } else {
            throw new AppointmentNotFoundException("Appointment to delete not found");
        }
    }
}
