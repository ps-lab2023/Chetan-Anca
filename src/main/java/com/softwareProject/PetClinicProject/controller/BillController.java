package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.service.BillService;
import com.softwareProject.PetClinicProject.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
@CrossOrigin(origins = "http://localhost:4200")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private EmailService emailService;

    @GetMapping()
    public void generateAndSendBill(@RequestParam String email, @RequestParam long id) throws MessagingException {
        billService.generateBill(id);
        emailService.sendMessageWithAttachment(email, id);
    }
}
