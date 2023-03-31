package com.softwareProject.PetClinicProject;

import com.softwareProject.PetClinicProject.model.*;
import com.softwareProject.PetClinicProject.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;


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
            doctor.setStartScheduleTime(LocalTime.of(8, 0, 0));
            doctor.setEndScheduleTime(LocalTime.of(14, 0, 0));
            doctorService.addDoctor(doctor);

            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setAnimal(animal1);
            appointment.setTime(LocalDateTime.of(2023, 4, 20, 14, 0, 0));
            appointmentService.addAppointment(appointment);

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
            doctorUpdate.setEndScheduleTime(LocalTime.of(15, 0, 0));
            doctorService.updateDoctor(doctorUpdate);

            System.out.println(ownerService.findById(owner.getOwnerId()).getAnimals());
            System.out.println(doctorService.findById(doctor.getDoctorId()).getAppointments());
            System.out.println(animalService.findById(animal1.getAnimalId()).getAppointments());

            //animalService.deleteById(animal1.getAnimalId());
            //ownerService.deleteById(owner.getOwnerId());
            //doctorService.deleteById(doctor.getDoctorId());
            //System.out.println(doctorService.findByFirstNameAndLastName("Ana", "Pop"));
        };

    }
}
