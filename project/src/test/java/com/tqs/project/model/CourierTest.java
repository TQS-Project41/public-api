package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class CourierTest {
    @Test
    void testWhenCreateValidCourierThenReturnCourier() {
        Date data = new Date();
        Courier c = new Courier(new User(), "Serras", "aaaaaaa", data);
        
        assertEquals("Serras", c.getName());
        assertEquals("aaaaaaa", c.getPhoto());

    }
}
