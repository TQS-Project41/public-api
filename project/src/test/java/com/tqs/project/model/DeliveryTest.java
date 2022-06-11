package com.tqs.project.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tqs.project.exception.BadLocationException;
import com.tqs.project.exception.BadPhoneNumberException;

import org.junit.jupiter.api.Test;

public class DeliveryTest {
    
    @Test
    void testWhenCreateValidDeliveryThenReturnDelivery() throws BadLocationException, BadPhoneNumberException {
        Delivery d = new Delivery();
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        d.setShop(s);
        d.setCourier(new Courier());
        d.getCourier().setName("Ser");
        d.setClient(new DeliveryContact("Serras", "910234123"));
        d.setId(1);
        d.setDeliveryAddress(new Address(10, -10));
        
        assertEquals("Pull Aveiro", d.getShop().getName());
        assertEquals(1, d.getId());
        assertEquals(10, d.getDeliveryAddress().getLatitude(),0.0001);
        assertEquals(-10, d.getDeliveryAddress().getLongitude(),0.0001);
        assertEquals("Ser", d.getCourier().getName());
        assertEquals("Serras", d.getClient().getName());
        assertEquals("910234123", d.getClient().getPhoneNumber());
    }

    @Test
    void testWhenCreateInValidDeliveryThenReturnBadPhoneNumberException() throws BadLocationException, BadPhoneNumberException {
        Delivery d = new Delivery();
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        d.setShop(s);
        d.setCourier(new Courier());
        d.getCourier().setName("Ser");
        assertThrows(BadPhoneNumberException.class, () -> {
            d.setClient(new DeliveryContact("Serras", "9110234123"));
        });
    }

    @Test
    void testWhenCreateInValidDeliveryThenReturnBadLocationException() throws BadLocationException, BadPhoneNumberException {
        Delivery d = new Delivery();
        Shop s = new Shop();
        s.setName("Pull Aveiro");
        d.setShop(s);
        d.setCourier(new Courier());
        d.getCourier().setName("Ser");
        assertThrows(BadLocationException.class, () -> {
            d.setDeliveryAddress(new Address(-100, -10));    
        });
    }
}
