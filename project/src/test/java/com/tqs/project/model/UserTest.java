package com.tqs.project.model;
import static org.junit.Assert.assertEquals;

import com.tqs.project.exception.UserAlreadyAssignedException;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testWhenCreateValidUserThenReturnUser() throws UserAlreadyAssignedException {

        User u = new User("x","xxxx");

        assertEquals(0, u.getId());
        assertEquals("x", u.getEmail());
        assertEquals("xxxx", u.getPassword());

    }

    @Test
    void testWhenCreateValidUserThenReturnUserv2() throws UserAlreadyAssignedException {

        User u = new User();
        u.setId(1);
        u.setEmail("x");
        u.setPassword("xxxx");

        assertEquals(1, u.getId());
        assertEquals("x", u.getEmail());
        assertEquals("xxxx", u.getPassword());
       
    }
}
