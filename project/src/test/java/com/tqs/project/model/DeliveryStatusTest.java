package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tqs.project.Model.DeliveryStatus;
import com.tqs.project.Model.DeliveryStatusEnum;

import org.junit.jupiter.api.Test;

public class DeliveryStatusTest {
    @Test
    void testWhenCreateValidDeliveryStatusThenReturnDeliveryStatus() {
        DeliveryStatus s = new DeliveryStatus();
        s.setDescription(DeliveryStatusEnum.DELIVERING);
        s.setId(1);
        assertEquals(1, s.getId());
        assertEquals("DELIVERING", s.getDescription().toString());

    }
}
