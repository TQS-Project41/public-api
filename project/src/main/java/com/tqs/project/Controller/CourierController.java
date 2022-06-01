package com.tqs.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tqs.project.Model.Courier;
import com.tqs.project.Service.CourierService;

@CrossOrigin
@RestController
public class CourierController {

    @Autowired
    private CourierService service;


    // create Courier
    @PostMapping("/courier")
    public ResponseEntity<String> createCourier(@RequestBody Courier courier) {
        service.save(courier);
        return ResponseEntity.status(HttpStatus.CREATED).body("Courier was created with success (CODE 201)\n");
    }
}