package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.OwnerDto;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.service.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<OwnerDto> register(@RequestBody OwnerDto ownerDto) {
        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner ownerReturned = ownerService.addOwner(owner);
        OwnerDto ownerDtoReturned = modelMapper.map(ownerReturned, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerDtoReturned);
    }
}
