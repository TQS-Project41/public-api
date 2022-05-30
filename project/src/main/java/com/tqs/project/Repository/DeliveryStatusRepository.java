package com.tqs.project.Repository;


import com.tqs.project.Model.DeliveryStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Long>  {
    
}
