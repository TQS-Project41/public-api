package com.tqs.project.controller;

import java.util.List;
import java.util.Optional;

import com.tqs.project.dto.DeliveryDto;
import com.tqs.project.exception.BadPhoneNumberException;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.DeliveryContact;
import com.tqs.project.model.Shop;
import com.tqs.project.service.CourierService;
import com.tqs.project.service.DeliveryService;
import com.tqs.project.service.ShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("delivery")
public class DeliveryController {

    // @Autowired
    // private DeliveryService service;

    // @Autowired
    // private ShopService shopService;

    // @Autowired
    // private CourierService courierService;

    // // TODO This endpoint should allow filtering
    // @GetMapping("")
    // public List<Delivery> getDelivery() {
    //     return service.getAllDeliveries();
    // }

    // @PostMapping("")
    // public ResponseEntity<String> createDelivery(@RequestBody DeliveryDto delivery) throws BadPhoneNumberException {
    //     Optional<Shop> shop = shopService.getShopById(delivery.getShopId());
    //     if (shop.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    //     DeliveryContact client = new DeliveryContact(delivery.getClientName(), delivery.getClientPhoneNumber());

    //     courierService.getCourierById(1);

    //     service.save(new Delivery(delivery.getDeliveryTimestamp(), null, client, shop.get(), new Courier()));
    //     return ResponseEntity.status(HttpStatus.CREATED).body("Delivery was created with success (CODE 201)\n");
    // }
}