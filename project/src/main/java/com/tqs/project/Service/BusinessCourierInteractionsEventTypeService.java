package com.tqs.project.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Repository.BusinessCourierInteractionsEventTypeRepository;

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

    public BusinessCourierInteractionsEventType update(BusinessCourierInteractionsEventType businessCourierInteractionsEventType) {
        BusinessCourierInteractionsEventType existingBusinessCourierInteractionsEventType = rep.findById( businessCourierInteractionsEventType.getId() ).orElse(null);

        if (businessCourierInteractionsEventType.getDescription() != null) existingBusinessCourierInteractionsEventType.setDescription(businessCourierInteractionsEventType.getDescription());
        if (businessCourierInteractionsEventType.getBusinessCourierInteractions() != null) businessCourierInteractionsEventType.setBusinessCourierInteractions(businessCourierInteractionsEventType.getBusinessCourierInteractions());

        return rep.save(existingBusinessCourierInteractionsEventType);
    }
}