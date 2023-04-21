package com.softwareProject.PetClinicProject.validator;

import com.softwareProject.PetClinicProject.exception.InvalidDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateValidator {

    public void validateDate(LocalDateTime date) throws InvalidDate {
        if (date.getMinute() != 0 && date.getMinute() != 30) {
            throw new InvalidDate("The hour of the appointment must be: hh:00 or hh:30!");
        }
    }
}
