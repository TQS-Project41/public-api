package com.tqs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.Delivery;

@Repository
public interface DeliveryRepository  extends JpaRepository<Delivery,Long> {
    
}