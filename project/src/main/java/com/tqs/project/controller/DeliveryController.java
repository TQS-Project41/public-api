package com.tqs.project.controller;

import java.util.List;

import com.tqs.project.model.Delivery;
import com.tqs.project.service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

    @GetMapping("")
    public List<Delivery> getDelivery() {
        return service.getAllDeliveries();
    }

    // TODO Replace Delivery with a DTO Class
    @PostMapping("")
    public ResponseEntity<String> createDelivery(@RequestBody Delivery delivery) {
        service.save(delivery);
        return ResponseEntity.status(HttpStatus.CREATED).body("Delivery was created with success (CODE 201)\n");
    }
}