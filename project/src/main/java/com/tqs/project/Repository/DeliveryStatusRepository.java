package com.tqs.project.Repository;


import com.tqs.project.Model.DeliveryStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Long>  {
    
}
