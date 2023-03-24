package com.softwareProject.PetClinicProject.exception;

public class InvalidOwnerException extends RuntimeException {

    public InvalidOwnerException() {
    }

    public InvalidOwnerException(String message) {
        super(message);
    }
}
