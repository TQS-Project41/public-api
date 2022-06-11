package com.tqs.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.tqs.project.exception.BadLocationException;

import org.junit.jupiter.api.Test;

public class ShopTest {
    @Test
    void testWhenCreateValidShopThenReturnShop() throws BadLocationException {
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        s.setAddress(new Address(50, -150));
        s.setId(1);

        assertEquals(1, s.getId());
        assertEquals("Pull Aveiro", s.getName());
        assertEquals(50, s.getAddress().getLatitude(),0.001);
        assertEquals(-150, s.getAddress().getLongitude(),0.001);

    }

    @Test
    void testWhenCreateInvalidValidShopThenReturnBadLocationException() throws BadLocationException {
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        assertThrows(BadLocationException.class, () -> {
            s.setAddress(new Address(50, -350));;
        });
       
        

    }
}
