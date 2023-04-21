package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@mitzuvet.com");
        message.setTo(to);
        message.setSubject("Authentication code");
        message.setText(code);
        System.out.println(message);
        emailSender.send(message);
    }
}
