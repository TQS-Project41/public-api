package com.tqs.project.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tqs.project.exception.BadLocationException;

import org.junit.jupiter.api.Test;

public class AddressTest{

    @Test
    void testWhenCreateValidAddressThenReturnAddress() throws BadLocationException{
        Address x = new Address(50,-180);
        
        assertEquals(50, x.getLatitude());
        assertEquals(-180, x.getLongitude());

        
        x.setLatitude(50);
        x.setLongitude(20);
        assertEquals(50, x.getLatitude());
        assertEquals(20, x.getLongitude());
    }

    @Test
    void testWhenCreateInvalidLatitudeThenReturnBadLocationException() throws BadLocationException{
        assertThrows(BadLocationException.class, () -> {
            new Address(-150,-180);
        });

    }

    @Test
    void testWhenCreateInvalidLongitudeThenReturnBadLocationException() throws BadLocationException{
        assertThrows(BadLocationException.class, () -> {
            new Address(0,-280);
        });

    }

    @Test
    void testWhenCreateInvalidLongitudeAndLatitudeThenReturnBadLocationException() throws BadLocationException{
        assertThrows(BadLocationException.class, () -> {
            new Address(-150,-280);
        });

    }

}