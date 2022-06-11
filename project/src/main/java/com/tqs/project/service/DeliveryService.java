package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.model.Delivery;
import com.tqs.project.repository.DeliveryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired(required = true)
    private DeliveryRepository rep;


    public Delivery save(Delivery delivery) {
        return rep.save(delivery);
    }

    public List<Delivery> saveAll(List<Delivery> deliveries) {
        return rep.saveAll(deliveries);
    }
    
    public List<Delivery> getAllDeliveries() {
        return rep.findAll();
    }

    public Optional<Delivery> getDeliveryById(long id) {
        return rep.findById(id);
    }
}