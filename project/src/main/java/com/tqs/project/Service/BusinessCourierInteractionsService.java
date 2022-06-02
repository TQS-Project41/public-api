package com.tqs.project.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.Model.BusinessCourierInteractions;
import com.tqs.project.Repository.BusinessCourierInteractionsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessCourierInteractionsService {

    @Autowired(required = true)
    private BusinessCourierInteractionsRepository rep;


    public BusinessCourierInteractions save(BusinessCourierInteractions businessCourierInteractions) {
        return rep.save(businessCourierInteractions);
    }

    public List<BusinessCourierInteractions> saveAll(List<BusinessCourierInteractions> businessCourierInteractionss) {
        return rep.saveAll(businessCourierInteractionss);
    }
    
    public List<BusinessCourierInteractions> getAllBusinessCourierInteractions() {
        return rep.findAll();
    }

    public Optional<BusinessCourierInteractions> getBusinessCourierInteractionsById(Long id) {
        return rep.findById(id);
    }

    public BusinessCourierInteractions update(BusinessCourierInteractions businessCourierInteractions) {
        BusinessCourierInteractions existingBusinessCourierInteractions = rep.findById( businessCourierInteractions.getId() ).orElse(null);

        if (businessCourierInteractions.getEvent() != null) existingBusinessCourierInteractions.setEvent(businessCourierInteractions.getEvent());

        return rep.save(existingBusinessCourierInteractions);
    }
}