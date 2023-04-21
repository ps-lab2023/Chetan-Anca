package com.softwareProject.PetClinicProject.service;

import com.softwareProject.PetClinicProject.exception.InvalidMedicalFacilityException;
import com.softwareProject.PetClinicProject.exception.MedicalFacilityNotFoundException;
import com.softwareProject.PetClinicProject.model.MedicalFacility;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MedicalFacilityService {
    MedicalFacility getMedicalFacilityById(long id) throws MedicalFacilityNotFoundException;

    MedicalFacility getMedicalFacilityByName(String name) throws MedicalFacilityNotFoundException;

    List<MedicalFacility> getMedicalFacilityByNameContaining(String name);

    List<MedicalFacility> getAllMedicalFacilitiesByPrice(float price);

    List<MedicalFacility> getAllMedicalFacilities();

    MedicalFacility addMedicalService(MedicalFacility medicalFacility) throws InvalidMedicalFacilityException;

    MedicalFacility updateMedicalService(MedicalFacility medicalFacility) throws MedicalFacilityNotFoundException, InvalidMedicalFacilityException;

    void deleteMedicalFacilityById(long id) throws MedicalFacilityNotFoundException;


}
