package com.softwareProject.PetClinicProject.exception;

public class MedicalFacilityNotFoundException extends RuntimeException {
    public MedicalFacilityNotFoundException() {
    }

    public MedicalFacilityNotFoundException(String message) {
        super(message);
    }
}
