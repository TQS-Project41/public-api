package com.tqs.project.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.project.dto.CourierDto;
import com.tqs.project.model.Business;
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.User;
import com.tqs.project.service.BusinessCourierInteractionsService;
import com.tqs.project.service.BusinessService;
import com.tqs.project.service.CourierService;
import com.tqs.project.service.DeliveryService;
import com.tqs.project.service.UserService;

@RestController
@RequestMapping("couriers")
public class CourierController {

  @Autowired
  private CourierService service;

  @Autowired
  private UserService userService;

  @Autowired
  private DeliveryService deliveryService;
  
  @Autowired
  private BusinessService businessService;

  @Autowired
  private BusinessCourierInteractionsService businessCourierInteractionsService;

  @PostMapping("")
  public ResponseEntity<Courier> register(@RequestBody CourierDto courier) {

    User user = new User(courier.getEmail(), courier.getPassword());
    Courier newCourier = new Courier(user, courier.getName(), courier.getPhoto(), courier.getBirthdate());

    return new ResponseEntity<>(service.save(newCourier), HttpStatus.CREATED);
  }

  @GetMapping("")
  public ResponseEntity<List<Courier>> getCouriers() {
    Optional<User> user_opt = userService.getAuthenticatedUser();
    if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    List<Courier> ret = service.getAllCouriers();
    return new ResponseEntity<>(ret, HttpStatus.OK);
  }

  @PostMapping("{id}/accept")
  public ResponseEntity<BusinessCourierInteractions> acceptCourier(@PathVariable(name = "id") int courierId) {

    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty())
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Business> business = businessService.getBusinessById(user.get().getId());
    if (business.isEmpty())
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Courier> courier = service.getCourierById(courierId);
    if (courier.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    Optional<BusinessCourierInteractions> interaction = businessCourierInteractionsService.accept(business.get(),
        courier.get());
    if (interaction.isEmpty())
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return ResponseEntity.status(HttpStatus.OK).body(interaction.get());

  }

  @PostMapping("{id}/block")
  public ResponseEntity<BusinessCourierInteractions> blockCourier(@PathVariable(name = "id") int courierId) {

    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Business> business = businessService.getBusinessById(user.get().getId());
    if (business.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Courier> courier = service.getCourierById(courierId);
    if (courier.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    Optional<BusinessCourierInteractions> interaction = businessCourierInteractionsService.block(business.get(), courier.get());
    if (interaction.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return ResponseEntity.status(HttpStatus.OK).body(interaction.get());

  }

  @GetMapping("applied")
  public ResponseEntity<List<Map<String, Object>>> getAllApplied() {
    
    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Business> business = businessService.getBusinessById(user.get().getId());
    if (business.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    
    List<BusinessCourierInteractions> appliedInteractions = businessCourierInteractionsService.getAllApplied(business.get());
    List<Map<String, Object>> result = appliedInteractions.stream().map(interaction -> Map.of("business", interaction.getBusiness().getUser().getEmail(), "event", Map.of("id", interaction.getEvent().ordinal(), "name", interaction.getEvent().name()), "timestamp", interaction.getTimestamp())).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(result);

  }
  @GetMapping("active")
  public ResponseEntity<List<Map<String, Object>>> getAllActive() {
    
    Optional<User> user = userService.getAuthenticatedUser();
    if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<Business> business = businessService.getBusinessById(user.get().getId());
    if (business.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    
    List<BusinessCourierInteractions> activeInteractions = businessCourierInteractionsService.getAllActive(business.get());
    List<Map<String, Object>> result = activeInteractions.stream().map(interaction -> Map.of("business", interaction.getBusiness().getUser().getEmail(), "event", Map.of("id", interaction.getEvent().ordinal(), "name", interaction.getEvent().name()), "timestamp", interaction.getTimestamp())).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(result);

  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Courier> getCourierById(@PathVariable int id) {
    Optional<User> user_opt = userService.getAuthenticatedUser();
    if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    Optional<Courier> courier_opt =  service.getCourierById(id);
    if (!courier_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    return new ResponseEntity<>(courier_opt.get(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Courier> putCourierById(@PathVariable int id, 
  @RequestParam(required = false,value="")  String name, @RequestParam(required = false,value="")  String password,
  @RequestParam(required = false,value="")  String photo) {
    Optional<User> user_opt = userService.getAuthenticatedUser();
    if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    Optional<Courier> courier_opt =  service.getCourierById(id);
    if (!courier_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    Courier courier= courier_opt.get();
    
    if (!name.equals("")){  
      courier.setName(name);
    }
    if (!password.equals("")){  
      courier.getUser().setPassword(password);
    }
    if (!photo.equals("")){  
      courier.setPhoto(photo);
    }
    service.save(courier);
    return new ResponseEntity<>(courier, HttpStatus.OK);
  }

  
  @PostMapping("/listen")
  public Page<Delivery> courierListen(Pageable pageable) {
    Optional<User> userOpt = userService.getAuthenticatedUser();
    if (!userOpt.isPresent())  return Page.empty();
    return deliveryService.deliveriesWithoutCourier(pageable);
  }


  
}
