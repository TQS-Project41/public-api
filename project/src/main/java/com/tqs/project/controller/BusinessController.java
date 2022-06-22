package com.tqs.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.project.dto.BusinessDto;
import com.tqs.project.model.Business;
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.Courier;
import com.tqs.project.model.User;
import com.tqs.project.service.BusinessCourierInteractionsService;
import com.tqs.project.service.BusinessService;
import com.tqs.project.service.CourierService;
import com.tqs.project.service.UserService;


@RestController
@RequestMapping("business")
public class BusinessController {

  @Autowired
  private BusinessService service;

  @Autowired
  private CourierService courierService;

  @Autowired
  private UserService userService;

  @Autowired
  private BusinessCourierInteractionsService businessCourierInteractionsService;

  @PostMapping("")
  public ResponseEntity<Business> register(@RequestBody BusinessDto business) {

    User user = new User(business.getEmail(), business.getPassword());
    Business newBusiness = new Business(user);

    return new ResponseEntity<>(service.save(newBusiness), HttpStatus.CREATED);

  }

  @PostMapping("{id}/apply")
  public ResponseEntity<BusinessCourierInteractions> applyBusiness(@PathVariable(name = "id") int businessId) {

    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Courier> courier = courierService.getCourierById(user.get().getId());
    if (courier.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Business> business = service.getBusinessById(businessId);
    if (business.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    Optional<BusinessCourierInteractions> interaction = businessCourierInteractionsService.apply(business.get(), courier.get());
    if (interaction.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return ResponseEntity.status(HttpStatus.OK).body(interaction.get());

  }

  @PostMapping("{id}/refuse")
  public ResponseEntity<BusinessCourierInteractions> refuseBusiness(@PathVariable(name = "id") int businessId) {
    
    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Courier> courier = courierService.getCourierById(user.get().getId());
    if (courier.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Business> business = service.getBusinessById(businessId);
    if (business.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    Optional<BusinessCourierInteractions> interaction = businessCourierInteractionsService.refuse(business.get(), courier.get());
    if (interaction.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return ResponseEntity.status(HttpStatus.OK).body(interaction.get());

  }

  @GetMapping("active")
  public ResponseEntity<List<Map<String, Object>>> getAllActive() {
    
    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Courier> courier = courierService.getCourierById(user.get().getId());
    if (courier.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    
    List<BusinessCourierInteractions> activeInteractions = businessCourierInteractionsService.getAllActive(courier.get());
    List<Map<String, Object>> result = activeInteractions.stream().map(interaction -> Map.of("business", interaction.getBusiness().getUser().getEmail(), "event", Map.of("id", interaction.getEvent().ordinal(), "name", interaction.getEvent().name()), "timestamp", interaction.getTimestamp())).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(result);

  }

  @GetMapping("")
  public ResponseEntity<List<Map<String, Object>>> getAll() {
    
    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Courier> courier = courierService.getCourierById(user.get().getId());
    if (courier.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    
    List<Map<String, Object>> result = new ArrayList<>();

    for (Business business : service.getAllBusinesss()) {
      Optional<BusinessCourierInteractions> lastInteraction = businessCourierInteractionsService.getLastInteraction(courier.get(), business);
      result.add(Map.of("business", business.getUser().getEmail(), "event", Map.of("id", lastInteraction.isPresent() ? lastInteraction.get().getEvent().ordinal() : -1, "name", lastInteraction.isPresent() ? lastInteraction.get().getEvent().name() : "NONE"), "timestamp", lastInteraction.isPresent() ? lastInteraction.get().getTimestamp() : 0));
    }

    return ResponseEntity.status(HttpStatus.OK).body(result);

  }
  
}
