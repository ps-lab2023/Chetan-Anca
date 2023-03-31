package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.model.Owner;
import com.softwareProject.PetClinicProject.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/findById")
    public ResponseEntity findOwnerById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findById(id));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity findOwnerByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findByEmail(email));
    }


    @GetMapping("/findByFullName")
    public ResponseEntity findOwnerByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("/findByCredentials")
    public ResponseEntity findOwnerByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findByEmailAndPassword(email, password));
    }

    @GetMapping()
    public ResponseEntity findAllOwners() {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity addOwner(@RequestBody Owner owner) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.addOwner(owner));
    }

    @PutMapping("/update")
    public ResponseEntity updateOwner(@RequestBody Owner owner) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.updateOwner(owner));
    }

    @DeleteMapping("/delete")
    public void deleteOwner(@RequestParam long id) {
        ownerService.deleteById(id);
    }
}
