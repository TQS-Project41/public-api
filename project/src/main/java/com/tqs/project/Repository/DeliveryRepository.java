package com.tqs.project.Repository;

import com.tqs.project.Model.Delivery;

import org.springframework.data.jpa.repository.JpaRepository;
public interface DeliveryRepository  extends JpaRepository<Delivery,Long> {
    
}
