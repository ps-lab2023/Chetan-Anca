package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.OwnerDto;
import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.service.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owner")
@CrossOrigin(origins = "http://localhost:4200")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/findById")
    public ResponseEntity getOwnerById(@RequestParam long id) {
        Owner owner = ownerService.getOwnerById(id);
        OwnerDto ownerDto = modelMapper.map(owner, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDto);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity getOwnerByEmail(@RequestParam String email) {
        Owner owner = ownerService.getOwnerByEmail(email);
        OwnerDto ownerDto = modelMapper.map(owner, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDto);
    }


    @GetMapping("/findByFullName")
    public ResponseEntity getOwnerByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        Owner owner = ownerService.getOwnerByFirstNameAndLastName(firstName, lastName);
        OwnerDto ownerDto = modelMapper.map(owner, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDto);
    }


    @GetMapping("/findByFirstName")
    public ResponseEntity getAllOwnersByFirstName(@RequestParam String firstName) {
        List<Owner> owners = ownerService.getAllByFirstNameContaining(firstName);
        List<OwnerDto> ownersDto = owners.stream()
                .map(owner -> modelMapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ownersDto);
    }

    @GetMapping("/findByLastName")
    public ResponseEntity getAllOwnersByLastName(@RequestParam String lastName) {
        List<Owner> owners = ownerService.getAllByLastNameContaining(lastName);
        List<OwnerDto> ownersDto = owners.stream()
                .map(owner -> modelMapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ownersDto);
    }

    @GetMapping("/findByCredentials")
    public ResponseEntity getOwnerByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        Owner owner = ownerService.getOwnerByEmailAndPassword(email, password);
        OwnerDto ownerDto = modelMapper.map(owner, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDto);
    }

    @GetMapping()
    public ResponseEntity getAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();
        List<OwnerDto> ownersDto = owners.stream()
                .map(owner -> modelMapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ownersDto);
    }

    @PostMapping("/add")
    public ResponseEntity addOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner ownerReturned = ownerService.addOwner(owner);
        OwnerDto ownerDtoReturned = modelMapper.map(ownerReturned, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerDtoReturned);
    }

    @PutMapping("/update")
    public ResponseEntity updateOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner ownerReturned = ownerService.updateOwner(owner);
        OwnerDto ownerDtoReturned = modelMapper.map(ownerReturned, OwnerDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDtoReturned);
    }

    @DeleteMapping("/delete")
    public void deleteOwner(@RequestParam long id) {
        ownerService.deleteOwnerById(id);
    }
}
