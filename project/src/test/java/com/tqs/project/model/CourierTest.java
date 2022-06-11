package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CourierTest {
    @Test
    void whenCreateValidCourierWithSetters_thenReturnCourier() {
        User user = new User();
        LocalDate birthdate = LocalDate.now();

        Courier c = new Courier(user, "Serras", "aaaaaaa", birthdate);

        c.setId(1);
        c.setUser(user);
        c.setName("Serras");
        c.setPhoto("aaaaaaa");
        c.setBirthdate(birthdate);

        assertEquals(1, c.getId());
        assertEquals(user, c.getUser());
        assertEquals("Serras", c.getName());
        assertEquals("aaaaaaa", c.getPhoto());
        assertEquals(birthdate, c.getBirthdate());
    }

    @Test
    void whenCreateValidCourierWithConstructor_thenReturnCourier() {
        User user = new User();
        LocalDate birthdate = LocalDate.now();

        Courier c = new Courier(user, "Serras", "aaaaaaa", birthdate);

        assertEquals(0, c.getId());
        assertEquals(user, c.getUser());
        assertEquals("Serras", c.getName());
        assertEquals("aaaaaaa", c.getPhoto());
        assertEquals(birthdate, c.getBirthdate());
    }

    @Test
    void whenCallingToString_thenReturnString() {
        assertNotEquals(null, new Courier().toString());
    }
}
