package com.tqs.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.tqs.project.exception.BadPhoneNumberException;

import org.junit.jupiter.api.Test;
public class DeliveryContactTest {
    
    @Test
    void testWhenCreateValidDeliveryContactThenReturnDeliveryContact() throws BadPhoneNumberException {
        DeliveryContact del = new DeliveryContact();
        del.setName("Serras");
        del.setPhoneNumber("912222123");
        assertEquals("Serras", del.getName());
        assertEquals("912222123", del.getPhoneNumber());
    }

    @Test
    void testWhenCreateInvalidValidDeliveryContactThenReturnBadPhoneNumberException() throws BadPhoneNumberException {
        DeliveryContact del = new DeliveryContact();
        del.setName("Serras");
        assertThrows(BadPhoneNumberException.class, () -> {
            del.setPhoneNumber("9122221123");

        });
    }

    @Test
    void testWhenCreateInvalidValidDeliveryContactThenReturnBadPhoneNumberException2() throws BadPhoneNumberException {
        DeliveryContact del = new DeliveryContact();
        del.setName("Serras");
        assertThrows(BadPhoneNumberException.class, () -> {
            del.setPhoneNumber("aaasssaaa");

        });
    }

}
