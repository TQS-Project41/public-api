package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeliveryStatusTest {
    @Test
    void whenCreateValidDeliveryStatusWithSetters_thenReturnDeliveryStatus() {
        DeliveryStatus s = new DeliveryStatus();

        s.setId(1);
        s.setDescription(DeliveryStatusEnum.DELIVERING);

        assertEquals(1, s.getId());
        assertEquals(DeliveryStatusEnum.DELIVERING, s.getDescription());
    }

    @Test
    void whenCreateValidDeliveryStatusWithConstructor_thenReturnDeliveryStatus() {
        DeliveryStatus s = new DeliveryStatus(DeliveryStatusEnum.DELIVERING);

        assertEquals(0, s.getId());
        assertEquals(DeliveryStatusEnum.DELIVERING, s.getDescription());
    }
}
