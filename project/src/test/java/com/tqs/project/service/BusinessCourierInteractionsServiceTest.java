package com.tqs.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.repository.BusinessCourierInteractionsRepository;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
 class BusinessCourierInteractionsServiceTest {

    @Mock(lenient = true)
    private BusinessCourierInteractionsRepository rep;

    @InjectMocks
    private BusinessCourierInteractionsService service;

    @BeforeEach
    public void setUp() {
        BusinessCourierInteractions b1 = new BusinessCourierInteractions();
        b1.setId(111L);

        BusinessCourierInteractions b2 = new BusinessCourierInteractions();
        b2.setId(222L);

        List<BusinessCourierInteractions> allBusinessCourierInteractions = Arrays.asList(b1, b2);

        Mockito.when(rep.findById(111L)).thenReturn(Optional.of(b1));
        Mockito.when(rep.findAll()).thenReturn(allBusinessCourierInteractions);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }


    @Test
     void whenSearchBusinessCourierInteractionsId_thenBusinessCourierInteractionsShouldBeFound() {
        Optional<BusinessCourierInteractions> found = service.getBusinessCourierInteractionsById( 111L );
        BusinessCourierInteractions b = null;
        if (found.isPresent()) b = found.get();

        assertThat(b.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenBusinessCourierInteractionsShouldNotBeFound() {
        Optional<BusinessCourierInteractions> found = service.getBusinessCourierInteractionsById(-99L);
        BusinessCourierInteractions b = null;
        if (found.isPresent()) b = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(b).isNull();
    }


    @Test
     void whengetAll_thenReturn2Records() {
        BusinessCourierInteractions b1 = new BusinessCourierInteractions();
        b1.setId(111L);

        BusinessCourierInteractions b2 = new BusinessCourierInteractions();
        b2.setId(222L);

        List<BusinessCourierInteractions> allBusinessCourierInteractionss = service.getAllBusinessCourierInteractions();

        // verify if FindAllBusinessCourierInteractionss is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allBusinessCourierInteractionss).hasSize(2).extracting(BusinessCourierInteractions::getId).contains(b1.getId(), b2.getId());
    }
 }