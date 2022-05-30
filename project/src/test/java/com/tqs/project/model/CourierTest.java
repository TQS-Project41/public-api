package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tqs.project.Model.BusinessCourierInteractions;
import com.tqs.project.Model.Courier;
import com.tqs.project.Model.Delivery;
import com.tqs.project.Model.User;

import org.junit.jupiter.api.Test;

public class CourierTest {
    @Test
    void testWhenCreateValidCourierThenReturnCourier() {
        Set<BusinessCourierInteractions> s = new HashSet<>();
        Set<Delivery> delivery = new HashSet<>();
        Delivery i = new Delivery();
        delivery.add(i);
        Date data = new Date();
        Courier c = new Courier(new User(), "Serras", "aaaaaaa", data, delivery, s);
        
        assertEquals("Serras", c.getName());
        assertEquals("aaaaaaa", c.getPhoto());
        assertEquals(1, c.getDelivery().size());
        assertEquals(0, c.getBusinessCourierInteractions().size());

    }
}
