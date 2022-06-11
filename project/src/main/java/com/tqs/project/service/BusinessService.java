package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.model.Business;
import com.tqs.project.repository.BusinessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    @Autowired(required = true)
    private BusinessRepository rep;


    public Business save(Business business) {
        return rep.save(business);
    }

    public List<Business> saveAll(List<Business> business) {
        return rep.saveAll(business);
    }
    
    public List<Business> getAllBusinesss() {
        return rep.findAll();
    }

    public Optional<Business> getBusinessById(long id) {
        return rep.findById(id);
    }
}