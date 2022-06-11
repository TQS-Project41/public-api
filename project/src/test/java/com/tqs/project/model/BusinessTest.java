package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusinessTest {

    @Test
    void whenCreateValidBusinessWithSetters_thenCreatesBussiness() {
        User u = new User();

        Business b = new Business();

        b.setId(1);
        b.setUser(u);

        assertEquals(1, b.getId());
        assertEquals(u, b.getUser());
    }

    @Test
    void whenCreateValidBusinessWithConstructor_thenCreatesBussiness() {
        User u = new User();

        Business b = new Business(u);

        assertEquals(0, b.getId());
        assertEquals(u, b.getUser());
    }
}
