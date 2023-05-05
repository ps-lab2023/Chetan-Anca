package com.softwareProject.PetClinicProject.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, long attachmentId) throws MessagingException;
}
