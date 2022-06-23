package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class BusinessCourierInteractionsTest {
    @Test
    void whenCreateValidBusinessCourierInteractionsWithSetters_thenReturnBusinessCourierInteractions() {
        Business bus = new Business();
        Courier c = new Courier();
        BusinessCourierInteractionsEventTypeEnum event = BusinessCourierInteractionsEventTypeEnum.ACCEPT;

        BusinessCourierInteractions b = new BusinessCourierInteractions();

        b.setId(1);
        b.setBusiness(bus);
        b.setCourier(c);
        b.setEvent(event);

        assertEquals(1, b.getId());
        assertEquals(bus, b.getBusiness());
        assertEquals(c, b.getCourier());
        assertEquals(event, b.getEvent());
    }

    @Test
    void whenCreateValidBusinessCourierInteractionsWithConstructor_thenReturnBusinessCourierInteractions() {
        Business business = new Business();
        Courier courier = new Courier();
        BusinessCourierInteractionsEventTypeEnum event = BusinessCourierInteractionsEventTypeEnum.ACCEPT;

        BusinessCourierInteractions b = new BusinessCourierInteractions(business, courier, event);

        assertEquals(0, b.getId());
        assertEquals(business, b.getBusiness());
        assertEquals(courier, b.getCourier());
        assertEquals(event, b.getEvent());
    }
}


