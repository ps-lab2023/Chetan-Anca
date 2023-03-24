package com.softwareProject.PetClinicProject;

import com.softwareProject.PetClinicProject.model.*;
import com.softwareProject.PetClinicProject.repository.*;
import com.softwareProject.PetClinicProject.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PetClinicProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetClinicProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService,
                           OwnerService ownerService,
                           DoctorService doctorService,
                           AppointmentService appointmentService,
                           AnimalService animalService) {
        return args -> {
            Owner owner = new Owner();
            owner.setFirstName("Anca");
            owner.setLastName("Chetan");
            owner.setEmail("ancach@yahoo.com");
            owner.setPassword("Parola@Mea123");
            owner.setPhoneNumber("0745382312");
            ownerService.addOwner(owner);

//            Owner owner1 = new Owner();
//            owner1.setFirstName("Anca");
//            owner1.setLastName("Chetan");
//            owner1.setEmail("ancach@yahoo.com");
//            owner1.setPassword("Parola@Mea123");
//            owner1.setPhoneNumber("0745382312");
//            ownerService.addOwner(owner);

            Animal animal1 = new Animal();
            animal1.setOwner(owner);
            animal1.setName("Mitzu");
            animal1.setType(AnimalType.CAT);
            animal1.setAge(1);
            animalService.addAnimal(animal1);

            Animal animal2 = new Animal();
            animal2.setOwner(owner);
            animal1.setName("Haze");
            animal2.setType(AnimalType.DOG);
            animal2.setAge(5);
            animalService.addAnimal(animal2);

            Doctor doctor = new Doctor();
            doctor.setFirstName("Ana");
            doctor.setLastName("Pop");
            doctor.setEmail("anaPop@yahoo.com");
            doctor.setPassword("PArola@Mea12");
            doctor.setPhoneNumber("0756326597");
            doctor.setStartScheduleTime(LocalTime.of(15, 30, 0));
            doctor.setEndScheduleTime(LocalTime.of(22, 0, 0));
            doctorService.addDoctor(doctor);

            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setAnimal(animal1);
            appointment.setTime(LocalDateTime.of(2023, Month.APRIL, 14, 14, 20, 0));
            appointmentService.addAppointment(appointment);

//            Appointment appointment1 = new Appointment();
//            appointment1.setDoctor(doctor);
//            appointment1.setAnimal(animal1);
//            appointment1.setTime(LocalDateTime.of(2023, Month.APRIL, 14, 14, 20, 0));
//            appointmentService.addAppointment(appointment1);

            Animal updateAnimal = new Animal();
            updateAnimal.setAnimalId(animal2.getAnimalId());
            updateAnimal.setName("Azorel");
            animalService.updateAnimal(updateAnimal);

            Owner ownerUpdate = new Owner();
            ownerUpdate.setOwnerId(owner.getOwnerId());
            ownerUpdate.setEmail("newemail@app.com");
            ownerService.updateOwner(ownerUpdate);

            Doctor doctorUpdate = new Doctor();
            doctorUpdate.setDoctorId(doctor.getDoctorId());
            doctorUpdate.setEndScheduleTime(LocalTime.of(20, 0, 0));
            doctorService.updateDoctor(doctorUpdate);

            //animalService.deleteById(animal1.getAnimalId());
            ownerService.deleteById(owner.getOwnerId());
            //doctorService.deleteById(doctor.getDoctorId());
        };

    }
}
