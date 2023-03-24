package com.softwareProject.PetClinicProject.validator;

import com.softwareProject.PetClinicProject.exception.WrongDetailsException;
import com.softwareProject.PetClinicProject.model.Doctor;
import com.softwareProject.PetClinicProject.model.User;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserDetailsValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[!#$%&'()*+,[-/]./:;<=>?@^_`{|}~]).{10,}$");

    private void validateEmail(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            throw new WrongDetailsException("Wrong email! Email must represent this format [email]@[app].[domain]");
        }
    }

    private void validatePassword(String password) {
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);
        if (!passwordMatcher.matches()) {
            throw new WrongDetailsException("Wrong password! Password should contain: at least 10 characters, at least 1 upper case, at least 1 lower case, at least 1 special character");
        }
    }

    public void validateUserDetails(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }
}
