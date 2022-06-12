package com.tqs.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.project.dto.BusinessDto;
import com.tqs.project.model.Business;
import com.tqs.project.model.User;
import com.tqs.project.service.BusinessService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("business")
public class BusinessController {

  @Autowired
  private BusinessService service;

  @PostMapping
  public ResponseEntity<Business> register(@RequestBody BusinessDto business) {

    User user = new User(business.getEmail(), business.getPassword());
    Business newBusiness = new Business(user);

    return new ResponseEntity<>(service.save(newBusiness), HttpStatus.CREATED);

  }
  
}
