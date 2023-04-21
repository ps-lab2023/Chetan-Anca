package com.softwareProject.PetClinicProject.exception;

public class InvalidDate extends RuntimeException {
    public InvalidDate() {
    }

    public InvalidDate(String message) {
        super(message);
    }
}
