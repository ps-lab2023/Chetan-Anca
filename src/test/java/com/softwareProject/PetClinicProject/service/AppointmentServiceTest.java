package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.*;
import com.softwareProject.PetClinicProject.model.*;
import com.softwareProject.PetClinicProject.repository.AppointmentRepository;
import com.softwareProject.PetClinicProject.service.impl.AppointmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private DoctorService doctorService;
    @Mock
    private AnimalService animalService;
    private AppointmentService appointmentService;
    private Appointment appointment;

    @Before
    public void setUp() {
        initMocks(this);
        appointmentService = new AppointmentServiceImpl(appointmentRepository, doctorService, animalService);
        appointment = new Appointment();
    }

    @Test
    public void givenExistingAppointmentId_whenFindById_thenFindAppointment() {
        appointment.setAppointmentId(1L);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        Optional<Appointment> foundAppointment = appointmentService.findById(appointment.getAppointmentId());

        assertNotNull(foundAppointment);
        assertEquals(1L, foundAppointment.get().getAppointmentId());
    }

    @Test
    public void givenNonExistingAppointmentId_whenFindById_thenFindAppointment() {
        appointment.setAppointmentId(400L);

        when(appointmentRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.findById(appointment.getAppointmentId());
        });
    }

    @Test
    public void givenDoctor_whenFindByDoctor_thenFindAppointments() {
        Doctor doctor = new Doctor(1L, "John", "Smith", "johnsmith@yahoo.com", "MyPassword@12",
                "0745382312", LocalTime.of(8, 0, 0), LocalTime.of(14, 0, 0), new ArrayList<>());
        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(doctor);
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(doctor);
        doctor.getAppointments().add(appointment1);
        doctor.getAppointments().add(appointment2);

        when(appointmentRepository.findAllByDoctor(doctor)).thenReturn(List.of(appointment1, appointment2));
        List<Appointment> appointments = appointmentService.findAllByDoctor(doctor);

        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertTrue(appointments.contains(appointment2));
    }

    @Test
    public void givenAnimal_whenFindByAnimal_thenFindAppointments() {
        Owner owner = new Owner(2L, "Mary", "Poppins", "marypoppins@gmail.com", "Password%12", "0742365986", new ArrayList<>());
        Animal animal = new Animal(1L, "Haze", owner, AnimalType.DOG, "Bulldog", 2, new ArrayList<>());
        Appointment appointment1 = new Appointment();
        appointment1.setAnimal(animal);
        Appointment appointment2 = new Appointment();
        appointment2.setAnimal(animal);
        animal.getAppointments().add(appointment1);
        animal.getAppointments().add(appointment2);

        when(appointmentRepository.findAllByAnimal(animal)).thenReturn(List.of(appointment1, appointment2));
        List<Appointment> appointments = appointmentService.findAllByAnimal(animal);

        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertTrue(appointments.contains(appointment1));
        assertTrue(appointments.contains(appointment2));
    }

    @Test
    public void givenValidAppointment_whenSaveAppointment_thenReturnSavedAppointment() {
        Doctor doctor = new Doctor(1L, "John", "Smith", "johnsmith@yahoo.com", "MyPassword@12",
                "0745382312", LocalTime.of(8, 0, 0), LocalTime.of(14, 0, 0), new ArrayList<>());
        Owner owner = new Owner(2L, "Mary", "Poppins", "marypoppins@gmail.com", "Password%12", "0742365986", new ArrayList<>());
        Animal animal = new Animal(1L, "Haze", owner, AnimalType.DOG, "Bulldog", 2, new ArrayList<>());
        appointment.setAppointmentId(1L);
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        appointment.setTime(LocalDateTime.of(2023, 4, 23, 14, 15, 00));

        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Optional<Appointment> savedAppointment = appointmentService.addAppointment(appointment);

        assertNotNull(savedAppointment);
        assertEquals(doctor, savedAppointment.get().getDoctor());
    }

    @Test
    public void givenNonExistingDoctor_whenSaveAppointment_thenThrowException() {
        Doctor doctor = new Doctor(400L, "John", "Doe", "johndoe@yahoo.com", "MyPassword@12",
                "0745382312", LocalTime.of(8, 0, 0), LocalTime.of(14, 0, 0), new ArrayList<>());
        Owner owner = new Owner(2L, "Mary", "Poppins", "marypoppins@gmail.com", "Password%12", "0742365986", new ArrayList<>());
        Animal animal = new Animal(1L, "Haze", owner, AnimalType.DOG, "Bulldog", 2, new ArrayList<>());
        appointment.setAppointmentId(1L);
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        appointment.setTime(LocalDateTime.of(2023, 4, 23, 14, 15, 00));

        willThrow(new DoctorNotFoundException()).given(doctorService).findById(doctor.getDoctorId());

        assertThrows(InvalidAppointmentException.class, () -> {
            appointmentService.addAppointment(appointment);
        });
    }

    @Test
    public void givenNonExistingAnimal_whenSaveAppointment_thenThrowException() {
        Doctor doctor = new Doctor(1L, "John", "Smith", "johnsmith@yahoo.com", "MyPassword@12",
                "0745382312", LocalTime.of(8, 0, 0), LocalTime.of(14, 0, 0), new ArrayList<>());
        Owner owner = new Owner(2L, "Mary", "Poppins", "marypoppins@gmail.com", "Password%12", "0742365986", new ArrayList<>());
        Animal animal = new Animal(400L, "Mitzu", owner, AnimalType.CAT, "American Shorthair", 2, new ArrayList<>());
        appointment.setAppointmentId(1L);
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        appointment.setTime(LocalDateTime.of(2023, 4, 23, 14, 15, 00));

        willThrow(new AnimalNotFoundException()).given(animalService).findById(animal.getAnimalId());

        assertThrows(InvalidAppointmentException.class, () -> {
            appointmentService.addAppointment(appointment);
        });
    }

    @Test
    public void givenExistingAppointment_whenUpdateAppointment_thenReturnSavedAppointment() {
        Doctor doctor = new Doctor(1L, "John", "Smith", "johnsmith@yahoo.com", "MyPassword@12",
                "0745382312", LocalTime.of(8, 0, 0), LocalTime.of(14, 0, 0), new ArrayList<>());
        Owner owner = new Owner(2L, "Mary", "Poppins", "marypoppins@gmail.com", "Password%12", "0742365986", new ArrayList<>());
        Animal animal = new Animal(1L, "Haze", owner, AnimalType.CAT, "American Shorthair", 2, new ArrayList<>());
        appointment.setAppointmentId(1L);
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        appointment.setTime(LocalDateTime.of(2023, 4, 23, 14, 15, 00));
        Appointment updateAppointment = new Appointment();
        updateAppointment.setAppointmentId(1L);
        updateAppointment.setTime(LocalDateTime.of(2023, 4, 23, 14, 16, 00));
        Appointment appointmentToReturn = new Appointment(1L, doctor,
                animal, LocalDateTime.of(2023, 4, 23, 14, 16, 00));

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointmentToReturn);

        Optional<Appointment> updatedAppointment = appointmentService.updateAppointmentTime(updateAppointment);

        assertNotNull(updatedAppointment);
        assertEquals(animal, updatedAppointment.get().getAnimal());
        assertEquals(LocalDateTime.of(2023, 4, 23, 14, 16, 00), updatedAppointment.get().getTime());
    }

    @Test
    public void givenNonExistingAppointment_whenUpdateAppointment_thenReturnSavedAppointment() {
        appointment.setAppointmentId(400L);

        when(appointmentRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.updateAppointmentTime(appointment);
        });
    }

    @Test
    public void givenExistingAppointment_whenDeleteAppointment_thenSuccess() {
        Doctor doctor = new Doctor(1L, "John", "Smith", "johnsmith@yahoo.com", "MyPassword@12",
                "0745382312", LocalTime.of(8, 0, 0), LocalTime.of(14, 0, 0), new ArrayList<>());
        Owner owner = new Owner(2L, "Mary", "Poppins", "marypoppins@gmail.com", "Password%12", "0742365986", new ArrayList<>());
        Animal animal = new Animal(1L, "Haze", owner, AnimalType.CAT, "American Shorthair", 2, new ArrayList<>());
        appointment.setAppointmentId(1L);
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        appointment.setTime(LocalDateTime.of(2023, 4, 23, 14, 15, 00));

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        doNothing().when(appointmentRepository).deleteById(appointment.getAppointmentId());

        appointmentService.deleteById(appointment.getAppointmentId());
        then(appointmentRepository).should().deleteById(1L);
    }

    @Test
    public void givenNonExistingAppointment_whenDeleteAppointment_thenThrowException() {
        appointment.setAppointmentId(400L);

        when(appointmentRepository.findById(400L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.deleteById(appointment.getAppointmentId());
        });
    }
}
