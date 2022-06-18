package com.tqs.project.controller;

import java.io.IOException;
import java.util.List;
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

@RestController
@RequestMapping("/shop")

public class ShopController {

    @Autowired
    private ShopService service;

    @Autowired
    private UserService userService;

    @Autowired
    private BusinessService businessService;
    @Autowired
    private NominatimService nominatimService;

    @GetMapping("")
    public ResponseEntity<List<Shop>> allShop() {

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Shop> shop =service.getAllShops();
        return  ResponseEntity.status(HttpStatus.OK).body(shop);
      }

    @GetMapping("{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable int id) {

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Shop> a =service.getShopById(id);
        if (!a.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Shop ret = a.get();

        return  ResponseEntity.status(HttpStatus.OK).body(ret);     
      }
    @PutMapping("{id}")
    public ResponseEntity<Shop> UpdateShopById(@PathVariable int id,@RequestParam String name) {

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Shop> a =service.getShopById(id);
        if (!a.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Shop ret = a.get();
        ret.setName(name);
        service.save(ret);
        return  ResponseEntity.status(HttpStatus.OK).body(ret);     
        
    }
  
      
      
    @PostMapping("")
    public ResponseEntity<Shop> createShop(@Valid @RequestBody ShopDto shop) throws BadLocationException, IOException, InterruptedException, ParseException {
        Optional<User> user = userService.getAuthenticatedUser();
        if (user.isEmpty()) return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();

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