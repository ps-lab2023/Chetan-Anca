package com.softwareProject.PetClinicProject.service;

import org.springframework.stereotype.Component;


@Component
public interface BillService {
    void generateBill(long id);
}
