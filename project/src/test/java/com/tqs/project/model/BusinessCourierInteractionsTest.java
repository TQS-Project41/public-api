package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class BusinessCourierInteractionsTest {
    @Test
    void testWhenCreateValidBusinessCourierInteractionsThenReturnBusinessCourierInteractions() {

        BusinessCourierInteractions b= new BusinessCourierInteractions();
        Business bus = new Business();
        Courier c = new Courier();
        BusinessCourierInteractionsEventType event= new BusinessCourierInteractionsEventType();
        c.setName("Serras");
        b.setBusiness(bus);
        b.setCourier(c);
        event.setDescription(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        b.setEvent(event);
        assertEquals(bus, b.getBusiness());
        assertEquals(c.getName(), b.getCourier().getName());
        assertEquals(event.getDescription().toString(), b.getEvent().getDescription().toString());


    }
}


