package com.softwareProject.PetClinicProject.repository;

import com.softwareProject.PetClinicProject.model.MedicalFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalFacilityRepository extends JpaRepository<MedicalFacility, Long> {
    Optional<MedicalFacility> findById(long id);

    Optional<MedicalFacility> findByName(String name);

    List<MedicalFacility> findAllByNameContaining(String substringName);

    List<MedicalFacility> findByPrice(float price);

    List<MedicalFacility> findAll();
}
