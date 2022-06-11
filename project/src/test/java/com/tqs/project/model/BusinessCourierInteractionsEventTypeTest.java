package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BusinessCourierInteractionsEventTypeTest {

    @Test
    void testWhenCreateValidBusinessCourierInteractionsEventTypeThenReturnBusinessCourierInteractionsEventType() {

        BusinessCourierInteractionsEventType b = new BusinessCourierInteractionsEventType();
        BusinessCourierInteractions bus= new BusinessCourierInteractions();
        bus.setEvent(b);
        BusinessCourierInteractions bus1= new BusinessCourierInteractions();
        Set<BusinessCourierInteractions> set= new  HashSet<>();
        bus1.setEvent(b);
        set.add(bus1);
        set.add(bus);
        b.setDescription(BusinessCourierInteractionsEventTypeEnum.BLOCK);

        assertEquals(b, bus1.getEvent());
        assertEquals(b.getDescription().toString(), bus.getEvent().getDescription().toString());


    }
}
