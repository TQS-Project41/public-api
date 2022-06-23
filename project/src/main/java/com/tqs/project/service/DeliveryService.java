package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.model.Delivery;
import com.tqs.project.repository.DeliveryRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired(required = true)
    private DeliveryRepository rep;

  

    public Delivery save(Delivery delivery) {
        return rep.save(delivery);
    }

    public Page<Delivery> deliveriesWithoutCourier(Pageable pageable){
        return rep.findByStatus(DeliveryStatusEnum.QUEUED, pageable);
    }   

    public Page<Delivery> getAll(Long courierId,Long shopId,Pageable pageable){

        Page<Delivery> page;
        if (courierId == null && shopId==null){
            page=rep.findAll(pageable);
        }
        else if(shopId==null){
            page=rep.findByCourierId(courierId, pageable);
        }
        else if(courierId==null){
            page=rep.findByShopId(shopId, pageable);
        }
        else{
            page=rep.findByCourierIdAndShopId(courierId,shopId, pageable);

        }
        return page;
    }

    public List<Delivery> getAllDeliveries() {
        return rep.findAll();
    }

    public Optional<Delivery> getDeliveryById(long id) {
        return rep.findById(id);
    }

    public Double getFee(){
        //6 COORDENADAS 
        return 5.0;
    }

}