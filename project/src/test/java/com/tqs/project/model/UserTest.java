package com.tqs.project.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.tqs.project.Exception.UserAlreadyAssignedException;
import com.tqs.project.Model.Business;
import com.tqs.project.Model.Courier;
import com.tqs.project.Model.User;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testWhenCreateValidUserThenReturnUser() throws UserAlreadyAssignedException {

        User u = new User("x","xxxx");
        u.setId(1);
        u.setBusiness(new Business());
        assertEquals(1, u.getId());
        assertEquals(0, u.getBusiness().getShop().size());

    }

    @Test
    void testWhenCreateValidUserThenReturnUserv2() throws UserAlreadyAssignedException {

        User u = new User("x","xxxx");
        u.setId(1);
        u.setCourier(new Courier());
       
    }

    @Test
    void testWhenCreateInValidUserThenReturnUserAlreadyAssignedException() throws UserAlreadyAssignedException {

        User u = new User("x","xxxx");
        u.setId(1);
        u.setCourier(new Courier());
        assertThrows(UserAlreadyAssignedException.class, () -> {
            u.setBusiness(new Business());
        });
    }
    @Test
    void testWhenCreateInValidUserThenReturnUserAlreadyAssignedException2() throws UserAlreadyAssignedException {

        User u = new User("x","xxxx");
        u.setId(1);
        u.setBusiness(new Business());
        assertThrows(UserAlreadyAssignedException.class, () -> {
            u.setCourier(new Courier());

        });
    }
}
