package com.tqs.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tqs.project.Model.Shop;
import com.tqs.project.Service.ShopService;

@CrossOrigin
@RestController
public class ShopController {

    @Autowired
    private ShopService service;


    // create Shop
    @PostMapping("/shop")
    public ResponseEntity<String> createShop(@RequestBody Shop shop) {
        service.save(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body("Shop was created with success (CODE 201)\n");
    }
}