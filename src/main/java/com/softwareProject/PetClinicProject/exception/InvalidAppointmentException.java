package com.softwareProject.PetClinicProject.exception;

public class InvalidAppointmentException extends RuntimeException {

    public InvalidAppointmentException() {
    }

    public InvalidAppointmentException(String message) {
        super(message);
    }
}
