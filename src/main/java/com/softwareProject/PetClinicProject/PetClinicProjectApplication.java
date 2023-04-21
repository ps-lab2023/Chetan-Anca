package com.softwareProject.PetClinicProject;

import com.softwareProject.PetClinicProject.model.*;
import com.softwareProject.PetClinicProject.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


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
                           AnimalService animalService,
                           MedicalFacilityService medicalFacilityService) {
        return args -> {

            User admin = new User();
            admin.setEmail("anca.chetan1706@yahoo.com");
            admin.setPassword("Parola@Mea123");
            admin.setUserType(UserType.ADMIN);
            userService.addUser(admin);

            Owner owner = new Owner();
            owner.setFirstName("Andreea");
            owner.setLastName("Dragus");
            owner.setEmail("dragusandreea33@yahoo.com");
            owner.setPassword("Andreea.1232");
            owner.setPhoneNumber("0751951499");
            ownerService.addOwner(owner);

            Owner owner2 = new Owner();
            owner2.setFirstName("Andreea");
            owner2.setLastName("Tabirta");
            owner2.setEmail("tabirtaandreea@yahoo.com");
            owner2.setPassword("Pisica#0601");
            owner2.setPhoneNumber("0754961930");
            ownerService.addOwner(owner2);

            Owner owner3 = new Owner();
            owner3.setFirstName("Alina");
            owner3.setLastName("Aurica");
            owner3.setEmail("aalina@yahoo.com");
            owner3.setPassword("OParolaNoua%12");
            owner3.setPhoneNumber("0765893125");
            ownerService.addOwner(owner3);

            Owner owner4 = new Owner();
            owner4.setFirstName("Andrei");
            owner4.setLastName("Popescu");
            owner4.setEmail("popescuandr@gmail.com");
            owner4.setPassword("MyPassword@12");
            owner4.setPhoneNumber("0745634851");
            ownerService.addOwner(owner4);

            Doctor doctor = new Doctor();
            doctor.setFirstName("Ana");
            doctor.setLastName("Pop");
            doctor.setEmail("anaPop@yahoo.com");
            doctor.setPassword("PArola@Mea12");
            doctor.setPhoneNumber("0756326597");
            doctor.setStartScheduleTime(LocalTime.of(8, 0, 0));
            doctor.setEndScheduleTime(LocalTime.of(14, 0, 0));
            doctorService.addDoctor(doctor);

            Doctor doctor1 = new Doctor();
            doctor1.setFirstName("Mihai");
            doctor1.setLastName("David");
            doctor1.setEmail("mihaiDavid@vet.com");
            doctor1.setPassword("MyPassAn@167");
            doctor1.setPhoneNumber("0763256841");
            doctor1.setStartScheduleTime(LocalTime.of(14, 0, 0));
            doctor1.setEndScheduleTime(LocalTime.of(20, 0, 0));
            doctorService.addDoctor(doctor1);

            Doctor doctor2 = new Doctor();
            doctor2.setFirstName("Ioana");
            doctor2.setLastName("Muresan");
            doctor2.setEmail("ioanamuresan@vet.com");
            doctor2.setPassword("VetClinic#124");
            doctor2.setPhoneNumber("0756328964");
            doctor2.setStartScheduleTime(LocalTime.of(8, 0, 0));
            doctor2.setEndScheduleTime(LocalTime.of(16, 0, 0));
            doctorService.addDoctor(doctor2);

            Doctor doctor3 = new Doctor();
            doctor3.setFirstName("Tudor");
            doctor3.setLastName("Popescu");
            doctor3.setEmail("popescutudor@vet.com");
            doctor3.setPassword("VetClinic#124");
            doctor3.setPhoneNumber("0785963245");
            doctor3.setStartScheduleTime(LocalTime.of(8, 0, 0));
            doctor3.setEndScheduleTime(LocalTime.of(16, 0, 0));
            doctorService.addDoctor(doctor3);

            MedicalFacility medicalFacility = new MedicalFacility();
            medicalFacility.setPrice(50);
            medicalFacility.setName("Vaccination");
            medicalFacilityService.addMedicalService(medicalFacility);

            MedicalFacility medicalFacility1 = new MedicalFacility();
            medicalFacility1.setPrice(100);
            medicalFacility1.setName("Consultation");
            medicalFacilityService.addMedicalService(medicalFacility1);

            MedicalFacility medicalFacility2 = new MedicalFacility();
            medicalFacility2.setPrice(75);
            medicalFacility2.setName("Deworming");
            medicalFacilityService.addMedicalService(medicalFacility2);

            MedicalFacility medicalFacility3 = new MedicalFacility();
            medicalFacility3.setPrice(200);
            medicalFacility3.setName("Castration");
            medicalFacilityService.addMedicalService(medicalFacility3);

            Animal animal1 = new Animal();
            animal1.setOwner(owner);
            animal1.setName("Mitzu");
            animal1.setType(AnimalType.CAT);
            animal1.setBreed("Scottish fold");
            animal1.setAge(1);
            animal1.setWeight(5);
            animalService.addAnimal(animal1);

            Animal animal2 = new Animal();
            animal2.setOwner(owner2);
            animal2.setName("Haze");
            animal2.setBreed("Bulldog");
            animal2.setType(AnimalType.DOG);
            animal2.setAge(5);
            animal2.setWeight(14);
            animalService.addAnimal(animal2);

            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setAnimal(animal1);
            appointment.setDate(LocalDateTime.of(2023, 4, 20, 13, 0, 0));
            List<MedicalFacility> medicalFacilityList = new ArrayList<>();
            medicalFacilityList.add(medicalFacility);
            medicalFacilityList.add(medicalFacility1);
            appointment.setMedicalFacilities(medicalFacilityList);
            appointmentService.addAppointment(appointment);
        };

    }
}
