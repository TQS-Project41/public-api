package com.tqs.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tqs.project.model.Business;
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.model.Courier;
import com.tqs.project.repository.BusinessCourierInteractionsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessCourierInteractionsService {

    @Autowired(required = true)
    private BusinessCourierInteractionsRepository rep;

    public BusinessCourierInteractions save(BusinessCourierInteractions businessCourierInteractions) {
        return rep.save(businessCourierInteractions);
    }

    public Optional<BusinessCourierInteractions> getById(Long id) {
        return rep.findById(id);
    }

    public Optional<BusinessCourierInteractions> getLastInteraction(Courier courier, Business business) {
        return rep.findFirstByBusinessAndCourierOrderByTimestampDesc(business, courier);
    }

    public List<BusinessCourierInteractions> getAll(Courier courier, Business business) {
        return rep.findByBusinessAndCourierOrderByTimestampDesc(business, courier);
    }

    public List<BusinessCourierInteractions> getAllActive(Courier courier) {
        List<BusinessCourierInteractions> interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        List<BusinessCourierInteractions> result = new ArrayList<>();

        for (BusinessCourierInteractions interaction : interactions) {
            Optional<BusinessCourierInteractions> lastInteraction = getLastInteraction(courier, interaction.getBusiness());
            if (lastInteraction.isPresent() && lastInteraction.get().getId() == interaction.getId())
                result.add(interaction);
        }

        return result;
    }

    public List<BusinessCourierInteractions> getAllActive(Business business) {
        List<BusinessCourierInteractions> interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        List<BusinessCourierInteractions> result = new ArrayList<>();

        for (BusinessCourierInteractions interaction : interactions) {
            Optional<BusinessCourierInteractions> lastInteraction = getLastInteraction(interaction.getCourier(), business);
            if (lastInteraction.isPresent() && lastInteraction.get().getId() == interaction.getId())
                result.add(interaction);
        }

        return result;
    }

    public Optional<BusinessCourierInteractions> apply(Business business, Courier courier) {
        Optional<BusinessCourierInteractions> lastInteraction = getLastInteraction(courier, business);
        if (lastInteraction.isPresent() && lastInteraction.get().getEvent() == BusinessCourierInteractionsEventTypeEnum.BLOCK)
            return Optional.empty();

        BusinessCourierInteractions interaction = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        return Optional.of(save(interaction));
    }

    public Optional<BusinessCourierInteractions> refuse(Business business, Courier courier) {
        Optional<BusinessCourierInteractions> lastInteraction = getLastInteraction(courier, business);
        if (lastInteraction.isEmpty() || lastInteraction.get().getEvent() != BusinessCourierInteractionsEventTypeEnum.ACCEPT)
            return Optional.empty();
        
        BusinessCourierInteractions interaction = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.REFUSE);
        return Optional.of(save(interaction));
    }

    public Optional<BusinessCourierInteractions> accept(Business business, Courier courier) {
        Optional<BusinessCourierInteractions> lastInteraction = getLastInteraction(courier, business);
        if (!lastInteraction.isPresent() || lastInteraction.get().getEvent() != BusinessCourierInteractionsEventTypeEnum.APPLY)
            return Optional.empty();
        
        BusinessCourierInteractions interaction = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        return Optional.of(save(interaction));
    }

    public Optional<BusinessCourierInteractions> block(Business business, Courier courier) {
        Optional<BusinessCourierInteractions> lastInteraction = getLastInteraction(courier, business);
        if (!lastInteraction.isPresent() || lastInteraction.get().getEvent() != BusinessCourierInteractionsEventTypeEnum.ACCEPT)
            return Optional.empty();
        
        BusinessCourierInteractions interaction = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.BLOCK);
        return Optional.of(save(interaction));
    }
}