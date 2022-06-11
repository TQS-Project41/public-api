package com.tqs.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.tqs.project.exception.BadLocationException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ShopTest {
    @Test
    void whenCreateValidShopWithSetters_thenReturnShop() throws BadLocationException {
        Address address = new Address();
        Business business = new Business();

        Shop s = new Shop();

        s.setId(1);
        s.setName("Pull Aveiro");
        s.setAddress(address);
        s.setBusiness(business);

        assertEquals(1, s.getId());
        assertEquals("Pull Aveiro", s.getName());
        assertEquals(address, s.getAddress());
        assertEquals(business, s.getBusiness());
    }

    @Test
    void whenCreateValidShopWithConstructor_thenReturnShop() throws BadLocationException {
        Address address = new Address();
        Business business = new Business();

        Shop s = new Shop("Pull Aveiro", address, business);

        assertEquals(0, s.getId());
        assertEquals("Pull Aveiro", s.getName());
        assertEquals(address, s.getAddress());
        assertEquals(business, s.getBusiness());
    }

    @Test
    void testWhenCreateInvalidValidShopThenReturnBadLocationException() throws BadLocationException {
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        assertThrows(BadLocationException.class, () -> {
            s.setAddress(new Address(50, -350));;
        });
    }

    @Test
    void whenCallingToString_thenReturnString() {
        assertNotEquals(null, new Shop().toString());
    }
}
