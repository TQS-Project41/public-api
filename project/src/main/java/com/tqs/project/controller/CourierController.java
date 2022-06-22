package com.tqs.project.controller;

import java.util.List;
import java.util.Optional;

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
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.User;
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
