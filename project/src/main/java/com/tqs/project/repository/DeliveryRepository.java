package com.tqs.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.model.Delivery;

@Repository
public interface DeliveryRepository  extends JpaRepository<Delivery,Long> {

    public Page<Delivery> findByCourierIdAndShopId(Long courier,Long shop,Pageable pageable);
    public Page<Delivery> findByShopId(Long shop,Pageable pageable);
    public Page<Delivery> findByCourierId(Long courier,Pageable pageable);
    public Page<Delivery> findByStatus(DeliveryStatusEnum id,Pageable pageable);


}
