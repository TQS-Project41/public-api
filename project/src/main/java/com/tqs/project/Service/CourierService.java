package com.tqs.project.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.Model.Courier;
import com.tqs.project.Repository.CourierRepository;

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

    public Courier update(Courier courier) {
        Courier existingCourier = rep.findById( courier.getId() ).orElse(null);

        if (courier.getPhoto() != null) existingCourier.setPhoto(courier.getPhoto());
        if (courier.getDelivery() != null) existingCourier.setDelivery(courier.getDelivery());
        if (courier.getBusinessCourierInteractions() != null) existingCourier.setBusinessCourierInteractions(courier.getBusinessCourierInteractions());

        return rep.save(existingCourier);
    }
}