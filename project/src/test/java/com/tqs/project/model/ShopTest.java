package com.tqs.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.tqs.project.Exception.BadLocationException;
import com.tqs.project.Model.Address;
import com.tqs.project.Model.Shop;

import org.junit.jupiter.api.Test;

public class ShopTest {
    @Test
    void testWhenCreateValidShopThenReturnShop() throws BadLocationException {
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        s.setShop_address(new Address(50, -150));
        s.setId(1);

        assertEquals(1, s.getId());
        assertEquals("Pull Aveiro", s.getName());
        assertEquals(50, s.getShop_address().getLatitude(),0.001);
        assertEquals(-150, s.getShop_address().getLongitude(),0.001);

    }

    @Test
    void testWhenCreateInvalidValidShopThenReturnBadLocationException() throws BadLocationException {
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        assertThrows(BadLocationException.class, () -> {
            s.setShop_address(new Address(50, -350));;
        });
       
        

    }
}
