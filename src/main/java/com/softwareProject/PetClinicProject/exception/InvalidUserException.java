package com.softwareProject.PetClinicProject.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
