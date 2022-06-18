package com.tqs.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tqs.project.dto.ShopDto;
import com.tqs.project.exception.BadLocationException;
import com.tqs.project.model.Address;
import com.tqs.project.model.Business;
import com.tqs.project.model.Shop;
import com.tqs.project.model.User;
import com.tqs.project.service.BusinessService;
import com.tqs.project.service.ShopService;
import com.tqs.project.service.UserService;

@CrossOrigin
@RestController
public class ShopController {

    @Autowired
    private ShopService service;

    @Autowired
    private UserService userService;

    @Autowired
    private BusinessService businessService;

    @PostMapping("/shop")
    public ResponseEntity<Shop> createShop(@RequestBody ShopDto shop) throws BadLocationException {
        Optional<User> user = userService.getAuthenticatedUser();
        if (user.isEmpty()) return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Optional<Business> business = businessService.getBusinessById(user.get().getId());
        if (business.isEmpty()) return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        
        // TODO Read from AddressDto and NominatimService
        Address address = new Address(0f, 0f);

        Shop shopCreated = service.save(new Shop(shop.getName(), address, business.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(shopCreated);
    }
}