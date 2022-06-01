package com.tqs.project.Repository;

import com.tqs.project.Model.BusinessCourierInteractionsEventType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCourierInteractionsEventTypeRepository
    extends JpaRepository<BusinessCourierInteractionsEventType,Long>  {
    
}
