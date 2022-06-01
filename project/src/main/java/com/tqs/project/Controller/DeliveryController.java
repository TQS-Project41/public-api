package com.tqs.project.Controller;

import java.util.List;

import com.tqs.project.Model.Delivery;
import com.tqs.project.Service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService service;


    // get delivery
    @GetMapping("/delivery")
    public List<Delivery> getDelivery() {
        return service.getAllDeliveries();
    }

    // create delivery
    @PostMapping("/delivery")
    public ResponseEntity<String> createDelivery(@RequestBody Delivery delivery) {
        service.save(delivery);
        return ResponseEntity.status(HttpStatus.CREATED).body("Delivery was created with success (CODE 201)\n");
    }
}