package com.tqs.project.Repository;

import com.tqs.project.Model.Delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository  extends JpaRepository<Delivery,Long> {
    
}
