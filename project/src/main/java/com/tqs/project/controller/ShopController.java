package com.tqs.project.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
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
import com.tqs.project.service.NominatimService;
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
    @Autowired
    private NominatimService nominatimService;

    @PostMapping("/shop")
    public ResponseEntity<Shop> createShop(@Valid @RequestBody ShopDto shop) throws BadLocationException, IOException, InterruptedException, ParseException {
        Optional<User> user = userService.getAuthenticatedUser();
        if (user.isEmpty()) return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        System.out.println("aaaaaaaaa");
        Optional<Business> business = businessService.getBusinessById(user.get().getId());
        if (business.isEmpty()) return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Map<String, Double> map = nominatimService.getAddress(shop.getAddress().getAddress(), shop.getAddress().getCity(), shop.getAddress().getZipcode(), shop.getAddress().getCountry());

        if (map==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Address address = new Address(map.get("lat"), map.get("lon"));

        Shop shopCreated = service.save(new Shop(shop.getName(), address, business.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(shopCreated);
    }
}