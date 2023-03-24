package com.softwareProject.PetClinicProject.exception;

public class InvalidAnimalException extends RuntimeException {

    public InvalidAnimalException() {
    }

    public InvalidAnimalException(String message) {
        super(message);
    }
}
