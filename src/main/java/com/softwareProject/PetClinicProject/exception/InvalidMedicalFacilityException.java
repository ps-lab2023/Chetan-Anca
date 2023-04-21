package com.softwareProject.PetClinicProject.exception;

public class InvalidMedicalFacilityException extends RuntimeException{
    public InvalidMedicalFacilityException() {
    }

    public InvalidMedicalFacilityException(String message) {
        super(message);
    }
}
