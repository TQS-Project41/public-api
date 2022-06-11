package com.tqs.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.DeliveryStatus;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Long>  {
    
}
