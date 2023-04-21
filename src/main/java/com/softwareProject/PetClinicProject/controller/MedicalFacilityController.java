package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.dto.MedicalFacilityDto;
import com.softwareProject.PetClinicProject.model.MedicalFacility;
import com.softwareProject.PetClinicProject.service.MedicalFacilityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/facility")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicalFacilityController {
    @Autowired
    private MedicalFacilityService medicalFacilityService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/findById")
    public ResponseEntity getMedicalFacilityById(@RequestParam long id) {
        MedicalFacility medicalFacility = medicalFacilityService.getMedicalFacilityById(id);
        MedicalFacilityDto medicalFacilityDto = modelMapper.map(medicalFacility, MedicalFacilityDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(medicalFacilityDto);
    }

    @GetMapping("/findByName")
    public ResponseEntity getMedicalFacilityByName(@RequestParam String name) {
        MedicalFacility medicalFacility = medicalFacilityService.getMedicalFacilityByName(name);
        MedicalFacilityDto medicalFacilityDto = modelMapper.map(medicalFacility, MedicalFacilityDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(medicalFacilityDto);
    }

    @GetMapping("/findByNameContaining")
    public ResponseEntity getMedicalFacilityByNameContaining(String name) {
        List<MedicalFacility> medicalFacilities = medicalFacilityService.getMedicalFacilityByNameContaining(name);
        List<MedicalFacilityDto> medicalFacilitiesDto = medicalFacilities.stream()
                .map(medicalFacility -> modelMapper.map(medicalFacility, MedicalFacilityDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(medicalFacilitiesDto);
    }

    @GetMapping("/findByPrice")
    public ResponseEntity getAllMedicalFacilitiesByPrice(@RequestParam int price) {
        List<MedicalFacility> medicalFacilities = medicalFacilityService.getAllMedicalFacilitiesByPrice(price);
        List<MedicalFacilityDto> medicalFacilitiesDto = medicalFacilities.stream()
                .map(medicalFacility -> modelMapper.map(medicalFacility, MedicalFacilityDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(medicalFacilitiesDto);
    }

    @GetMapping()
    public ResponseEntity getAllMedicalFacilities() {
        List<MedicalFacility> medicalFacilities = medicalFacilityService.getAllMedicalFacilities();
        List<MedicalFacilityDto> medicalFacilitiesDto = medicalFacilities.stream()
                .map(medicalFacility -> modelMapper.map(medicalFacility, MedicalFacilityDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(medicalFacilitiesDto);
    }

    @PostMapping("/add")
    public ResponseEntity addMedicalFacility(@RequestBody MedicalFacilityDto medicalFacilityDto) {
        MedicalFacility medicalFacility = modelMapper.map(medicalFacilityDto, MedicalFacility.class);
        MedicalFacility medicalFacilityReturned = medicalFacilityService.addMedicalService(medicalFacility);
        MedicalFacilityDto medicalFacilityDtoReturned = modelMapper.map(medicalFacilityReturned, MedicalFacilityDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalFacilityDtoReturned);
    }

    @PutMapping("/update")
    public ResponseEntity updateMedicalFacility(@RequestBody MedicalFacilityDto medicalFacilityDto) {
        MedicalFacility medicalFacility = modelMapper.map(medicalFacilityDto, MedicalFacility.class);
        MedicalFacility medicalFacilityReturned = medicalFacilityService.updateMedicalService(medicalFacility);
        MedicalFacilityDto medicalFacilityDtoReturned = modelMapper.map(medicalFacilityReturned, MedicalFacilityDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(medicalFacilityDtoReturned);
    }

    @DeleteMapping("/delete")
    public void deleteAppointment(@RequestParam long id) {
        medicalFacilityService.deleteMedicalFacilityById(id);
    }
}
