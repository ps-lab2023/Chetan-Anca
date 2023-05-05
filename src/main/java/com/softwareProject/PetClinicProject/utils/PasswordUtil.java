package com.softwareProject.PetClinicProject.utils;

import java.util.Random;

public class PasswordUtil {
    private static final char[] UPPER_CASE_ALPHA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final char[] LOWER_CASE_ALPHA = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] CHARACTERS = {'!', '#', '$', '%', '&', '@'};

    public static String generateUniquePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            password.append(LOWER_CASE_ALPHA[random.nextInt(0, LOWER_CASE_ALPHA.length - 1)]);
        }

        for (int i = 0; i < 2; i++) {
            password.append(UPPER_CASE_ALPHA[random.nextInt(0, UPPER_CASE_ALPHA.length - 1)]);
        }

        for (int i = 0; i < 2; i++) {
            password.append(CHARACTERS[random.nextInt(0, CHARACTERS.length - 1)]);
        }

        for (int i = 0; i < 2; i++) {
            password.append(DIGITS[random.nextInt(0, DIGITS.length - 1)]);
        }
        return password.toString();
    }
}
