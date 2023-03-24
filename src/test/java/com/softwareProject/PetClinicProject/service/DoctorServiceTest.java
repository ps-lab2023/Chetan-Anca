package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.*;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.User;
import com.softwareProject.PetClinicProject.model.UserType;
import com.softwareProject.PetClinicProject.repository.DoctorRepository;
import com.softwareProject.PetClinicProject.service.impl.DoctorServiceImpl;
import com.softwareProject.PetClinicProject.validator.DoctorDetailsValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private DoctorDetailsValidator doctorDetailsValidator;
    @Mock
    private UserService userService;
    private DoctorService doctorService;

    @Before
    public void setUp() {
        initMocks(this);
        doctorService = new DoctorServiceImpl(doctorRepository, doctorDetailsValidator, userService);
    }

    @Test
    public void givenExistingDoctorId_whenFindById_thenFindDoctor() {
        Doctor doctor = createValidDoctor();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        Optional<Doctor> foundDoctor = doctorService.findById(doctor.getDoctorId());

        assertNotNull(foundDoctor);
        assertEquals(1L, foundDoctor.get().getDoctorId());
    }

    @Test
    public void givenNonExistingDoctorrId_whenFindById_thenThrowException() {
        Doctor doctor = createNonExistingDoctor();

        when(doctorRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.findById(doctor.getDoctorId());
        });
    }

    @Test
    public void givenExistingEmailAndPassword_whenFindByEmailAndPassword_thenFindDoctor() {
        Doctor doctor = createValidDoctor();

        when(doctorRepository.findByEmailAndPassword("johnsmith@yahoo.com", "MyPassword.12")).thenReturn(Optional.of(doctor));
        Optional<Doctor> foundDoctor = doctorService.findByEmailAndPassword("johnsmith@yahoo.com", "MyPassword.12");

        assertNotNull(foundDoctor);
        assertEquals("johnsmith@yahoo.com", foundDoctor.get().getEmail());
        assertEquals("MyPassword.12", foundDoctor.get().getPassword());
    }

    @Test
    public void givenNonExistingEmailAndPassword_whenFindByEmailAndPassword_thenThrowException() {
        Doctor doctor = createNonExistingDoctor();

        when(doctorRepository.findByEmailAndPassword("marysmith@gmail.ro", "Password@12")).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.findByEmailAndPassword(doctor.getEmail(), doctor.getPassword());
        });
    }

    @Test
    public void givenExistingFirstNameAndLastName_whenFindByFirstNameAndLastName_thenFindDoctor() {
        Doctor doctor = createValidDoctor();

        when(doctorRepository.findByFirstNameAndLastName("John", "Smith")).thenReturn(Optional.of(doctor));
        Optional<Doctor> foundDoctor = doctorService.findByFirstNameAndLastName("John", "Smith");

        assertNotNull(foundDoctor);
        assertEquals("John", foundDoctor.get().getFirstName());
        assertEquals("Smith", foundDoctor.get().getLastName());
    }

    @Test
    public void givenNonExistingFirstNameAndLastName_whenFindByFirstNameAndLastName_thenFindDoctor() {
        Doctor doctor = createNonExistingDoctor();

        when(doctorRepository.findByFirstNameAndLastName("Mary", "Smith")).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.findByFirstNameAndLastName(doctor.getFirstName(), doctor.getLastName());
        });
    }

    @Test
    public void givenValidDoctor_whenSaveDoctor_thenReturnSavedDoctor() {
        Doctor doctor = createValidDoctor();
        User user = new User(doctor.getDoctorId(), doctor.getEmail(), doctor.getPassword(), UserType.DOCTOR);

        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(userService.addUser(user)).thenReturn(Optional.of(user));
        Optional<Doctor> savedDoctor = doctorService.addDoctor(doctor);

        assertTrue(savedDoctor.isPresent());
        assertEquals("johnsmith@yahoo.com", savedDoctor.get().getEmail());
        then(userService).should().addUser(user);
    }

    @Test
    public void givenInvalidDoctor_whenSaveDoctor_thenThrowException() {
        Doctor doctor = createInvalidDoctor();
        willThrow(new WrongDetailsException()).given(doctorDetailsValidator).validateDoctorDetails(doctor);

        assertThrows(InvalidDoctorException.class, () -> {
            doctorService.addDoctor(doctor);
        });
    }

    @Test
    public void givenExistingDoctor_whenUpdateDoctor_thenReturnUpdatedDoctor() {
        Doctor doctor = createValidDoctor();
        Doctor doctorUpdate = new Doctor();
        doctorUpdate.setDoctorId(1L);
        doctorUpdate.setEmail("johnsmith12@yahoo.com");
        doctorUpdate.setEndScheduleTime(LocalTime.of(15, 0, 0));
        Doctor doctorToReturn = new Doctor();
        doctorToReturn.setDoctorId(doctor.getDoctorId());
        doctorToReturn.setFirstName(doctor.getFirstName());
        doctorToReturn.setLastName(doctor.getLastName());
        doctorToReturn.setEmail(doctorUpdate.getEmail());
        doctorToReturn.setPassword(doctor.getPassword());
        doctorToReturn.setPhoneNumber(doctor.getPhoneNumber());
        doctorToReturn.setStartScheduleTime(doctor.getStartScheduleTime());
        doctorToReturn.setEndScheduleTime(doctorUpdate.getEndScheduleTime());
        User user = new User(doctorToReturn.getDoctorId(), doctorToReturn.getEmail(), doctor.getPassword(), UserType.DOCTOR);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctorUpdate)).thenReturn(doctorUpdate);
        when(userService.addUser(user)).thenReturn(Optional.of(user));
        Optional<Doctor> updatedDoctor = doctorService.updateDoctor(doctorUpdate);

        assertNotNull(updatedDoctor);
        assertEquals("johnsmith12@yahoo.com", updatedDoctor.get().getEmail());
        assertEquals("0745382312", updatedDoctor.get().getPhoneNumber());
        assertEquals(LocalTime.of(15, 0, 0), updatedDoctor.get().getEndScheduleTime());
        then(userService).should().addUser(user);
    }

    @Test
    public void givenNonExistingDoctor_whenUpdateDoctor_thenThrowException() {
        Doctor doctorUpdate = new Doctor();
        doctorUpdate.setDoctorId(400L);

        when(doctorRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.updateDoctor(doctorUpdate);
        });
    }

    @Test
    public void givenDoctorWithInvalidDetails_whenUpdateDoctor_thenThrowException() {
        Doctor doctor = createValidDoctor();
        Doctor doctorUpdate = new Doctor();
        doctorUpdate.setDoctorId(1L);
        doctorUpdate.setEmail("johnsmith12");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        willThrow(new WrongDetailsException()).given(doctorDetailsValidator).validateDoctorDetails(doctor);

        assertThrows(InvalidDoctorException.class, () -> {
            doctorService.updateDoctor(doctorUpdate);
        });
    }

    @Test
    public void givenExistingOwner_whenDeleteOwner_thenSuccess() {
        Doctor doctor = createValidDoctor();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).deleteById(doctor.getDoctorId());

        doctorService.deleteById(doctor.getDoctorId());
        then(doctorRepository).should().deleteById(1L);
    }

    @Test
    public void givenNonExistingUser_whenDeleteUser_thenThrowException() {
        Doctor doctor = createInvalidDoctor();

        when(doctorRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.deleteById(doctor.getDoctorId());
        });
    }

    private Doctor createValidDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Smith");
        doctor.setEmail("johnsmith@yahoo.com");
        doctor.setPassword("MyPassword.12");
        doctor.setPhoneNumber("0745382312");
        doctor.setStartScheduleTime(LocalTime.of(8, 0, 0));
        doctor.setEndScheduleTime(LocalTime.of(14, 0, 0));
        return doctor;
    }

    private Doctor createInvalidDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(400L);
        doctor.setFirstName("Mary");
        doctor.setLastName("Smith");
        doctor.setEmail("marysmith.ro");
        doctor.setPassword("Password@12");
        doctor.setPhoneNumber("0745382312");
        doctor.setStartScheduleTime(LocalTime.of(8, 0, 0));
        doctor.setEndScheduleTime(LocalTime.of(14, 0, 0));
        return doctor;
    }

    public Doctor createNonExistingDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(400L);
        doctor.setFirstName("Mary");
        doctor.setLastName("Smith");
        doctor.setEmail("marysmith@gmail.ro");
        doctor.setPassword("Password@12");
        doctor.setPhoneNumber("0745382312");
        doctor.setStartScheduleTime(LocalTime.of(8, 0, 0));
        doctor.setEndScheduleTime(LocalTime.of(14, 0, 0));
        return doctor;
    }
}

