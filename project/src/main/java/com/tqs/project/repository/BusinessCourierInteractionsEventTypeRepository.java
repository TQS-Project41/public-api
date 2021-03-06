package com.tqs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.BusinessCourierInteractionsEventType;

@Repository
public interface BusinessCourierInteractionsEventTypeRepository
    extends JpaRepository<BusinessCourierInteractionsEventType,Long>  {
    
}
