package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.DoctorNotFoundException;
import com.softwareProject.PetClinicProject.exception.InvalidDoctorException;
import com.softwareProject.PetClinicProject.exception.InvalidOwnerException;
import com.softwareProject.PetClinicProject.model.*;
import com.softwareProject.PetClinicProject.service.DoctorService;
import com.softwareProject.PetClinicProject.service.UserService;
import com.softwareProject.PetClinicProject.validator.DoctorDetailsValidator;
import com.softwareProject.PetClinicProject.exception.WrongDetailsException;
import com.softwareProject.PetClinicProject.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorDetailsValidator doctorDetailsValidator;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorDetailsValidator doctorDetailsValidator, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.doctorDetailsValidator = doctorDetailsValidator;
        this.userService = userService;
    }

    @Override
    public Doctor getDoctorById(long id) throws DoctorNotFoundException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (!doctor.isPresent()) {
            throw new DoctorNotFoundException("Doctor with id " + id + " not found");
        }
        return doctor.get();
    }

    @Override
    public Doctor getDoctorByEmail(String email) throws DoctorNotFoundException {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (!doctor.isPresent()) {
            throw new DoctorNotFoundException("Doctor with email " + email + " not found");
        }
        return doctor.get();
    }

    @Override
    public List<Doctor> getAllDoctorsByFirstName(String firstName) {
        return doctorRepository.findAllByFirstName(firstName);
    }

    @Override
    public List<Doctor> getAllDoctorsByLastName(String lastName) {
        return doctorRepository.findAllByLastName(lastName);
    }

    @Override
    public Doctor getDoctorByFirstNameAndLastName(String firstName, String lastName) {
        Optional<Doctor> doctor = doctorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (!doctor.isPresent()) {
            throw new DoctorNotFoundException("Doctor with first name " + firstName + " and last name " + lastName + " not found");
        }
        return doctor.get();
    }


    @Override
    public Doctor getDoctorByEmailAndPassword(String email, String password) throws DoctorNotFoundException {
        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email, password);
        if (!doctor.isPresent()) {
            throw new DoctorNotFoundException("Doctor with email " + email + " and password " + password + " not found");
        }
        return doctor.get();
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor addDoctor(Doctor doctor) throws InvalidDoctorException {
        try {
            doctorDetailsValidator.validateDoctorDetails(doctor);
            Optional<Doctor> foundDoctor = doctorRepository.findByEmail(doctor.getEmail());
            if (!foundDoctor.isPresent()) {
                User user = new User(doctor.getDoctorId(), doctor.getEmail(), doctor.getPassword(), UserType.DOCTOR, false, null, null);
                userService.addUser(user);
                doctor.setDoctorId(user.getId());
                doctorRepository.save(doctor);
            } else {
                throw new InvalidDoctorException("Doctor with this email already exists");
            }
        } catch (WrongDetailsException exp) {
            throw new InvalidDoctorException(exp.getMessage());
        }
        return doctor;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) throws InvalidDoctorException, DoctorNotFoundException {
        Optional<Doctor> doctorToUpdate = doctorRepository.findById(doctor.getDoctorId());
        Doctor finalDoctor;
        if (doctorToUpdate.isPresent()) {
            User userToUpdate = userService.getUserById(doctorToUpdate.get().getDoctorId());
            User finalUser = createUser(doctor, userToUpdate);
            finalDoctor = createDoctor(doctor, doctorToUpdate.get());
            try {
                doctorDetailsValidator.validateDoctorDetails(finalDoctor);
            } catch (WrongDetailsException exp) {
                throw new InvalidDoctorException(exp.getMessage());
            }
            doctorRepository.save(finalDoctor);
            userService.updateUser(finalUser);
        } else {
            throw new DoctorNotFoundException("Doctor to add not found");
        }
        return finalDoctor;
    }

    @Override
    public Doctor updatePassword(long id, String password) throws InvalidDoctorException, DoctorNotFoundException {
        Optional<Doctor> doctorToUpdate = doctorRepository.findById(id);
        doctorToUpdate.get().setPassword(password);
        try {
            doctorDetailsValidator.validateDoctorDetails(doctorToUpdate.get());
        } catch (WrongDetailsException exp) {
            throw new InvalidDoctorException(exp.getMessage());
        }
        doctorToUpdate.get().setPassword(passwordEncoder.encode(password));
        User userToUpdate = userService.getUserById(id);
        User finalUser = createUser(doctorToUpdate.get(), userToUpdate);
        userService.updateUser(finalUser);
        return doctorRepository.save(doctorToUpdate.get());
    }


    @Override
    public void deleteDoctorById(long id) throws DoctorNotFoundException {
        Optional<Doctor> doctorToDelete = doctorRepository.findById(id);
        if (doctorToDelete.isPresent()) {
            userService.deleteUserById(id);
            doctorRepository.deleteById(id);
        } else {
            throw new DoctorNotFoundException("Doctor to delete not found");
        }
    }

    private User createUser(Doctor doctor, User userToUpdate) {
        if (doctor.getEmail() != null) {
            userToUpdate.setEmail(doctor.getEmail());
        }
        if (doctor.getPassword() != null) {
            userToUpdate.setPassword(doctor.getPassword());
        }
        return userToUpdate;
    }

    private Doctor createDoctor(Doctor doctor, Doctor doctorToUpdate) {
        if (doctor.getFirstName() != null) {
            doctorToUpdate.setFirstName(doctor.getFirstName());
        }
        if (doctor.getLastName() != null) {
            doctorToUpdate.setLastName(doctor.getLastName());
        }
        if (doctor.getEmail() != null) {
            doctorToUpdate.setEmail(doctor.getEmail());
        }
        if (doctor.getPhoneNumber() != null) {
            doctorToUpdate.setPhoneNumber(doctor.getPhoneNumber());
        }
        if (doctor.getStartScheduleTime() != null) {
            doctorToUpdate.setStartScheduleTime(doctor.getStartScheduleTime());
        }
        if (doctor.getEndScheduleTime() != null) {
            doctorToUpdate.setEndScheduleTime(doctor.getEndScheduleTime());
        }
        return doctorToUpdate;
    }
}
