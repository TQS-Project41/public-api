package com.tqs.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.BusinessCourierInteractions;

@Repository
public interface BusinessCourierInteractionsRepository  extends JpaRepository<BusinessCourierInteractions,Long> {
    
}
