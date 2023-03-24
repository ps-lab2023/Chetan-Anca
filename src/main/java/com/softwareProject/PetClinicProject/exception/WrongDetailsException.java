package com.softwareProject.PetClinicProject.exception;

public class WrongDetailsException extends RuntimeException {

    public WrongDetailsException() {
    }

    public WrongDetailsException(String message) {
        super(message);
    }
}
