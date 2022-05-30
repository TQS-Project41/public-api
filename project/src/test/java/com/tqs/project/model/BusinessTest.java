package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.tqs.project.Model.Business;
import com.tqs.project.Model.BusinessCourierInteractions;
import com.tqs.project.Model.Shop;
import com.tqs.project.Model.User;

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
        Set<BusinessCourierInteractions> bus= new  HashSet<>();
        u.setUsername("Serras");
        b.setUser(u);
        b.setBusinessCourierInteractions(bus);
        b.setShop(shops);
        assertEquals(0, b.getBusinessCourierInteractions().size());
        assertEquals(2, b.getShop().size());
        assertEquals("Serras", b.getUser().getUsername());
    }
}
