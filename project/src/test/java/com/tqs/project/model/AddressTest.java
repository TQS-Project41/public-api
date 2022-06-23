package com.tqs.project.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void testWhenCreateInvalidLongitudeAndLatitudeThenReturnBadLocationException() throws BadLocationException {
        assertThrows(BadLocationException.class, () -> {
            new Address(-150, -280);
        });

    }

    @Test
    void testWhenCreateInvalidLongitudeAndLatitude_thenReturnBadLocationException() throws BadLocationException {
        assertThrows(BadLocationException.class, () -> {
            new Address(-150, -280);
        });

    }

    @Test
    void testWhenCreateInvalidLatitudeSetterThenReturnBadLocationException() throws BadLocationException {
        assertThrows(BadLocationException.class, () -> {
            new Address().setLatitude(-150);
        });

    }

    @Test
    void testWhenCreateInvalidPositiveLatitudeSetterThenReturnBadLocationException() throws BadLocationException {
        assertThrows(BadLocationException.class, () -> {
            new Address().setLatitude(150);
        });

    }

    @Test
    void testWhenCreateInvalidLongitudeSetterThenReturnBadLocationException() throws BadLocationException {
        assertThrows(BadLocationException.class, () -> {
            new Address().setLongitude(-280);
        });

    }

    @Test
    void testWhenCreateInvalidPositiveLongitudeSetterThenReturnBadLocationException() throws BadLocationException {
        assertThrows(BadLocationException.class, () -> {
            new Address().setLongitude(280);
        });

    }

    @Test
    void whenCallingToString_thenReturnString() {
        assertNotEquals(null, new Address().toString());
    }

    @Test
    void whenGettingDistance_thenReturnNonNegativeNumber() {
        assertEquals(0, new Address().getDistance(new Address()));
    }

}