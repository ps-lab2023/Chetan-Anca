package com.softwareProject.PetClinicProject.exception;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException() {
    }

    public AnimalNotFoundException(String message) {
        super(message);
    }
}
