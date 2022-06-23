package com.tqs.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.tqs.project.service.UserService;
import com.tqs.project.model.Address;
import com.tqs.project.model.Business;
import com.tqs.project.dto.DeliveryDto;
import com.tqs.project.exception.BadLocationException;
import com.tqs.project.exception.BadPhoneNumberException;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.DeliveryContact;
import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.model.Shop;
import com.tqs.project.model.User;
import com.tqs.project.service.BusinessService;
import com.tqs.project.service.CourierService;
import com.tqs.project.service.DeliveryService;
import com.tqs.project.service.NominatimService;
import com.tqs.project.service.ShopService;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @Autowired
    private BusinessService busService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private NominatimService nominatimService;

    @GetMapping("")
    public Page<Delivery> getDelivery(Pageable pageable) {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return Page.empty();
        User user = user_opt.get();

        Optional<Courier> couriers = courierService.getCourierById(user.getId());
        if (couriers.isPresent())  return service.getAll(user.getId(), null, pageable);
        
        Optional<Business> bus = shopService.getShopByBusinessId(user.getId());
        if (bus.isPresent())  return service.getAll(null, user.getId(), pageable);

        return Page.empty();
    }

    @GetMapping("/courier/{id}")
    public Page<Delivery> getDeliveriesByCourierAndBusiness(@PathVariable long id, Pageable pageable) { // id do courier
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return Page.empty();
        User user = user_opt.get();

        Optional<Courier> couriers = courierService.getCourierById(id);
        if (!couriers.isPresent())  return Page.empty();
        
        Optional<Business> bus = shopService.getShopByBusinessId(user.getId());
        if (bus.isPresent())  return service.getAll(id, user.getId(), pageable);

        return Page.empty();
    }

    @GetMapping("/fee")
    public ResponseEntity<Map<String, Double>> deliveryFee() {
        Map<String,Double> ret = new HashMap<>();
        ret.put("fee",service.getFee());
        return ResponseEntity.status(HttpStatus.OK).body(ret);
 
    }

    @PostMapping("")
    public ResponseEntity<Delivery> createDelivery(@Valid @RequestBody DeliveryDto delivery) throws BadPhoneNumberException, IOException, InterruptedException, ParseException, BadLocationException {

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        Optional<Business> bus_opt = busService.getBusinessById(user.getId());
        if (!bus_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Map<String, Double> coordenadas_store = nominatimService.getAddress(delivery.getShopAddress().getAddress(),delivery.getShopAddress().getCity() , delivery.getShopAddress().getZipcode(), delivery.getShopAddress().getCountry());
        if (!coordenadas_store.containsKey("lat")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Shop shop = new Shop(delivery.getShopAddress().getAddress(), new Address(coordenadas_store.get("lat"),coordenadas_store.get("lon")), bus_opt.get());
        shop=shopService.save(shop);

        DeliveryContact client = new DeliveryContact(delivery.getClientName(), delivery.getClientPhoneNumber());
        
        Map<String, Double> coordenadas = nominatimService.getAddress(delivery.getAddress().getAddress(),delivery.getAddress().getCity() , delivery.getAddress().getZipcode(), delivery.getAddress().getCountry());
        if (coordenadas.containsKey("lat")){
            Address address = new Address(coordenadas.get("lat"), coordenadas.get("lon"));
            Delivery del = new Delivery(delivery.getDeliveryTimestamp(), address, client, shop, null);
            del=service.save(del);
            return ResponseEntity.status(HttpStatus.CREATED).body(del); 
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
       
    }

    @GetMapping("{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable int id) throws BadPhoneNumberException {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Delivery> delivery_opt =  service.getDeliveryById(id);
        if (!delivery_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(delivery_opt.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Delivery> deleteDelivery(@PathVariable int id) throws BadPhoneNumberException {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Delivery> delivery_opt =  service.getDeliveryById(id);
        if (!delivery_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Delivery del = delivery_opt.get();

        if (del.getStatus() == DeliveryStatusEnum.QUEUED ){

            del.setStatus(DeliveryStatusEnum.CANCELLED);

            service.save(del);
            return ResponseEntity.status(HttpStatus.OK).body(del);

        }
        return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("{id}/accept")
    public ResponseEntity<Delivery> acceptDelivery(@PathVariable int id) throws BadPhoneNumberException {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Delivery> delivery_opt =  service.getDeliveryById(id);
        if (!delivery_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Courier> courier_opt =  courierService.getCourierById(user_opt.get().getId());
        if (!courier_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Courier courier_choosen = courier_opt.get();

        Delivery del = delivery_opt.get();
        if (del.getStatus() == DeliveryStatusEnum.QUEUED ){
            del.setCourier(courier_choosen);
            //SET STATUS DELA AQUI
            del.setStatus(DeliveryStatusEnum.COLLECTING);

            service.save(del);
            return ResponseEntity.status(HttpStatus.OK).body(del);
        }
        return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        
    }

    @PutMapping("{id}/collect")
    public ResponseEntity<Delivery> collectDelivery(@PathVariable int id) throws BadPhoneNumberException {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Delivery> delivery_opt =  service.getDeliveryById(id);
        if (!delivery_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Courier> courier_opt =  courierService.getCourierById(user_opt.get().getId());
        if (!courier_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Courier courier_choosen = courier_opt.get();


        Delivery del = delivery_opt.get();
        if (del.getStatus() == DeliveryStatusEnum.COLLECTING && del.getCourier().getId() == courier_choosen.getId() ){
            del.setStatus(DeliveryStatusEnum.DELIVERING);

            service.save(del);
            return ResponseEntity.status(HttpStatus.OK).body(del);
        }
        //SET STATUS DELA AQUI
        return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        
    }

    @PutMapping("{id}/deliver")
    public ResponseEntity<Delivery> deliverDelivery(@PathVariable int id) throws BadPhoneNumberException {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Delivery> delivery_opt =  service.getDeliveryById(id);
        if (!delivery_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Courier> courier_opt =  courierService.getCourierById(user_opt.get().getId());
        if (!courier_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Courier courier_choosen = courier_opt.get();


        Delivery del = delivery_opt.get();
        if (del.getStatus() == DeliveryStatusEnum.DELIVERING && del.getCourier().getId() == courier_choosen.getId() ){
            del.setStatus(DeliveryStatusEnum.DELIVERED);

            service.save(del);
            return ResponseEntity.status(HttpStatus.OK).body(del);
        
        }
        return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        
    }
}