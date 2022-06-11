package com.tqs.project.model;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testWhenCreateValidUserThenReturnUser() {

        User u = new User("x","xxxx");

        assertEquals(0, u.getId());
        assertEquals("x", u.getEmail());
        assertEquals("xxxx", u.getPassword());

    }

    @Test
    void testWhenCreateValidUserThenReturnUserv2() {

        User u = new User();
        u.setId(1);
        u.setEmail("x");
        u.setPassword("xxxx");

        assertEquals(1, u.getId());
        assertEquals("x", u.getEmail());
        assertEquals("xxxx", u.getPassword());
       
    }

    @Test
    void whenCallingToString_thenReturnString() {
        assertNotEquals(null, new User().toString());
    }
}
