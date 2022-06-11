package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BusinessTest {
    
    @Test
    void testWhenCreateValidBusinessThenReturnBussiness() {

        Business b = new Business();
        User u = new User();
        Shop s = new Shop();
        Shop s1 = new Shop();
        Set<Shop> shops= new HashSet<>();
        shops.add(s1);
        shops.add(s);
        u.setUsername("Serras");
        b.setUser(u);

        assertEquals("Serras", b.getUser().getUsername());
    }
}
