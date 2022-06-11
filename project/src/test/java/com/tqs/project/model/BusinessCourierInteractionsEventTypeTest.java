package com.tqs.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusinessCourierInteractionsEventTypeTest {

    @Test
    void whenCreateValidBusinessCourierInteractionsEventTypeWithSetters_thenReturnBusinessCourierInteractionsEventType() {

        BusinessCourierInteractionsEventType b = new BusinessCourierInteractionsEventType();

        b.setId(1);
        b.setDescription(BusinessCourierInteractionsEventTypeEnum.BLOCK);

        assertEquals(1, b.getId());
        assertEquals(BusinessCourierInteractionsEventTypeEnum.BLOCK, b.getDescription());

    }

    @Test
    void whenCreateValidBusinessCourierInteractionsEventTypeWithConstructor_thenReturnBusinessCourierInteractionsEventType() {

        BusinessCourierInteractionsEventType b = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.BLOCK);

        assertEquals(0, b.getId());
        assertEquals(BusinessCourierInteractionsEventTypeEnum.BLOCK, b.getDescription());

    }
}
