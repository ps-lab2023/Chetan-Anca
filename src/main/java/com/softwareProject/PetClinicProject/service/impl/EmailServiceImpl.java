package com.softwareProject.PetClinicProject.service.impl;

import com.softwareProject.PetClinicProject.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@mitzuvet.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println(message);
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String to, long attachmentId) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@baeldung.com");
        helper.setTo(to);
        helper.setSubject("Appointment bill");
        helper.setText("We are happy you choose us again! Can't wait to see you next time");

        FileSystemResource file
                = new FileSystemResource(new File("Appointment" + attachmentId + "_bill" + ".pdf"));
        helper.addAttachment("Appointment" + attachmentId + "_bill" + ".pdf", file);

        emailSender.send(message);
    }
}
