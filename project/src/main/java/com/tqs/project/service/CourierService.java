package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.model.Courier;
import com.tqs.project.repository.CourierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourierService {

    @Autowired(required = true)
    private CourierRepository rep;


    public Courier save(Courier courier) {
        return rep.save(courier);
    }

    public List<Courier> saveAll(List<Courier> couriers) {
        return rep.saveAll(couriers);
    }
    
    public List<Courier> getAllCouriers() {
        return rep.findAll();
    }

    public Optional<Courier> getCourierById(long id) {
        return rep.findById(id);
    }
}