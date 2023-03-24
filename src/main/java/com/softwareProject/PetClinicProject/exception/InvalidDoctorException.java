package com.softwareProject.PetClinicProject.exception;

public class InvalidDoctorException extends RuntimeException {

    public InvalidDoctorException() {
    }

    public InvalidDoctorException(String message) {
        super(message);
    }
}
