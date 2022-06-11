package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.model.BusinessCourierInteractionsEventType;
import com.tqs.project.repository.BusinessCourierInteractionsEventTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessCourierInteractionsEventTypeService {

    @Autowired(required = true)
    private BusinessCourierInteractionsEventTypeRepository rep;


    public BusinessCourierInteractionsEventType save(BusinessCourierInteractionsEventType BusinessCourierInteractionsEventType) {
        return rep.save(BusinessCourierInteractionsEventType);
    }

    public List<BusinessCourierInteractionsEventType> saveAll(List<BusinessCourierInteractionsEventType> BusinessCourierInteractionsEventType) {
        return rep.saveAll(BusinessCourierInteractionsEventType);
    }
    
    public List<BusinessCourierInteractionsEventType> getAllBusinessCourierInteractionsEventTypes() {
        return rep.findAll();
    }

    public Optional<BusinessCourierInteractionsEventType> getBusinessCourierInteractionsEventTypeById(long id) {
        return rep.findById(id);
    }
}