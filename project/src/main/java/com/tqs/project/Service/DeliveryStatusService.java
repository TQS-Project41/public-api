package com.tqs.project.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.Model.DeliveryStatus;
import com.tqs.project.Repository.DeliveryStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryStatusService {

    @Autowired(required = true)
    private DeliveryStatusRepository rep;


    public DeliveryStatus save(DeliveryStatus delivery) {
        return rep.save(delivery);
    }

    public List<DeliveryStatus> saveAll(List<DeliveryStatus> deliveries) {
        return rep.saveAll(deliveries);
    }
    
    public List<DeliveryStatus> getAllDeliveries() {
        return rep.findAll();
    }

    public Optional<DeliveryStatus> getDeliveryStatusById(long id) {
        return rep.findById(id);
    }

    public DeliveryStatus update(DeliveryStatus delivery) {
        DeliveryStatus existingDeliveryStatus = rep.findById( delivery.getId() ).orElse(null);

        if (existingDeliveryStatus != null) {
            if (delivery.getDescription() != null) existingDeliveryStatus.setDescription(delivery.getDescription());

            return rep.save(existingDeliveryStatus);
        }
        return null;
    }
}