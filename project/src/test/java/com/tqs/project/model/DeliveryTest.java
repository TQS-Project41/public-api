package com.tqs.project.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.tqs.project.exception.BadLocationException;
import com.tqs.project.exception.BadPhoneNumberException;

import org.junit.jupiter.api.Test;

public class DeliveryTest {
    
    @Test
    void whenCreateValidDeliveryWithSetters_thenReturnDelivery() throws BadLocationException, BadPhoneNumberException {
        LocalDateTime deliveryTimestamp = LocalDateTime.now();
        Address deliveryAddress = new Address();
        DeliveryContact client = new DeliveryContact();
        Shop shop = new Shop();
        Courier courier = new Courier();

        Delivery d = new Delivery();
        
        d.setId(1);
        d.setShop(shop);
        d.setCourier(courier);
        d.setClient(client);
        d.setDeliveryAddress(deliveryAddress);
        d.setDeliveryTimestamp(deliveryTimestamp);
        
        assertEquals(1, d.getId());
        assertEquals(shop, d.getShop());
        assertEquals(courier, d.getCourier());
        assertEquals(client, d.getClient());
        assertEquals(deliveryAddress, d.getDeliveryAddress());
        assertEquals(deliveryTimestamp, d.getDeliveryTimestamp());
        assertEquals(null, d.getTimestamp());
    }

    @Test
    void whenCreateValidDeliveryWithFullConstructor_thenReturnDelivery() throws BadLocationException, BadPhoneNumberException {
        LocalDateTime deliveryTimestamp = LocalDateTime.now();
        Address deliveryAddress = new Address();
        DeliveryContact client = new DeliveryContact();
        Shop shop = new Shop();
        Courier courier = new Courier();

        Delivery d = new Delivery(deliveryTimestamp, deliveryAddress, client, shop, courier);

        assertEquals(0, d.getId());
        assertEquals(deliveryTimestamp, d.getDeliveryTimestamp());
        assertEquals(deliveryAddress, d.getDeliveryAddress());
        assertEquals(client, d.getClient());
        assertEquals(shop, d.getShop());
        assertEquals(null, d.getTimestamp());
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
