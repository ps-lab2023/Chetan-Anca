package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.exception.InvalidMedicalFacilityException;
import com.softwareProject.PetClinicProject.exception.MedicalFacilityNotFoundException;
import com.softwareProject.PetClinicProject.exception.OwnerNotFoundException;
import com.softwareProject.PetClinicProject.model.MedicalFacility;
import com.softwareProject.PetClinicProject.repository.MedicalFacilityRepository;
import com.softwareProject.PetClinicProject.service.MedicalFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalFacilityServiceImpl implements MedicalFacilityService {
    @Autowired
    private MedicalFacilityRepository medicalFacilityRepository;

    @Override
    public MedicalFacility getMedicalFacilityById(long id) throws MedicalFacilityNotFoundException {
        Optional<MedicalFacility> medicalFacility = medicalFacilityRepository.findById(id);
        if (!medicalFacility.isPresent()) {
            throw new MedicalFacilityNotFoundException("Medical facility with id " + id + " not found!");
        }
        return medicalFacility.get();
    }

    @Override
    public MedicalFacility getMedicalFacilityByName(String name) throws MedicalFacilityNotFoundException {
        Optional<MedicalFacility> medicalFacility = medicalFacilityRepository.findByName(name);
        if (!medicalFacility.isPresent()) {
            throw new MedicalFacilityNotFoundException("Medical facility with name " + name + " not found!");
        }
        return medicalFacility.get();
    }

    @Override
    public List<MedicalFacility> getMedicalFacilityByNameContaining(String name) {
        return medicalFacilityRepository.findAllByNameContaining(name);
    }

    @Override
    public List<MedicalFacility> getAllMedicalFacilitiesByPrice(float price) {
        return medicalFacilityRepository.findByPrice(price);
    }

    @Override
    public List<MedicalFacility> getAllMedicalFacilities() {
        return medicalFacilityRepository.findAll();
    }

    @Override
    public MedicalFacility addMedicalService(MedicalFacility medicalFacility) throws InvalidMedicalFacilityException {
        if (medicalFacility.getName() == null) {
            throw new InvalidMedicalFacilityException("Medical facility must have a name!");
        }
        if (medicalFacility.getPrice() <= 0) {
            throw new InvalidMedicalFacilityException("Medical facility must have a valid price");
        }
        medicalFacilityRepository.save(medicalFacility);
        return medicalFacility;
    }

    @Override
    public MedicalFacility updateMedicalService(MedicalFacility medicalFacility) throws MedicalFacilityNotFoundException, InvalidMedicalFacilityException {
        Optional<MedicalFacility> medicalFacilityToUpdate = medicalFacilityRepository.findById(medicalFacility.getId());
        if (medicalFacilityToUpdate.isPresent()) {
            if (medicalFacility.getPrice() != 0) {
                medicalFacilityToUpdate.get().setPrice(medicalFacility.getPrice());
            }
            if (medicalFacility.getName() != null) {
                medicalFacilityToUpdate.get().setName(medicalFacility.getName());
            }
            medicalFacilityRepository.save(medicalFacilityToUpdate.get());
        } else {
            throw new OwnerNotFoundException("Medical facility to update not found");
        }

        return medicalFacilityToUpdate.get();
    }

    @Override
    public void deleteMedicalFacilityById(long id) throws MedicalFacilityNotFoundException {
        Optional<MedicalFacility> medicalFacilityToDelete = medicalFacilityRepository.findById(id);
        if (medicalFacilityToDelete.isPresent()) {
            medicalFacilityRepository.deleteById(id);
        } else {
            throw new OwnerNotFoundException("Medical facility to delete not found");
        }
    }
}
