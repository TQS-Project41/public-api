package com.tqs.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.project.dto.CourierDto;
import com.tqs.project.model.Courier;
import com.tqs.project.model.User;
import com.tqs.project.service.CourierService;

@RestController
@RequestMapping("couriers")
public class CourierController {

  @Autowired
  private CourierService service;

  @PostMapping
  public ResponseEntity<Courier> register(@RequestBody CourierDto courier) {

    User user = new User(courier.getEmail(), courier.getPassword());
    Courier newCourier = new Courier(user, courier.getName(), courier.getPhoto(), courier.getBirthdate());

    return new ResponseEntity<>(service.save(newCourier), HttpStatus.CREATED);

  }
  
}
