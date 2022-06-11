package com.tqs.project.service;

import java.util.Optional;

import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.repository.BusinessCourierInteractionsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessCourierInteractionsService {

    @Autowired(required = true)
    private BusinessCourierInteractionsRepository rep;

    public BusinessCourierInteractions save(BusinessCourierInteractions businessCourierInteractions) {
        return rep.save(businessCourierInteractions);
    }

    public Optional<BusinessCourierInteractions> getById(Long id) {
        return rep.findById(id);
    }
}