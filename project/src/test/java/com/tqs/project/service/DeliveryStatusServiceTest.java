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

import com.tqs.project.model.DeliveryStatus;
import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.repository.DeliveryStatusRepository;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
 class DeliveryStatusServiceTest {

    @Mock( lenient = true)
    private DeliveryStatusRepository rep;

    @InjectMocks
    private DeliveryStatusService service;

    @BeforeEach
    public void setUp() {

        DeliveryStatus d1 = new DeliveryStatus(DeliveryStatusEnum.COLLECTING);
        d1.setId(111L);

        DeliveryStatus d2 = new DeliveryStatus(DeliveryStatusEnum.CANCELLED);
        DeliveryStatus d3 = new DeliveryStatus(DeliveryStatusEnum.DELIVERED);
        DeliveryStatus d4 = new DeliveryStatus(DeliveryStatusEnum.QUEUED);
        DeliveryStatus d5 = new DeliveryStatus(DeliveryStatusEnum.DELIVERING);

        List<DeliveryStatus> allDeliveries = Arrays.asList(d1, d2, d3, d4, d5);

        Mockito.when(rep.findById(d1.getId())).thenReturn(Optional.of(d1));
        Mockito.when(rep.findAll()).thenReturn(allDeliveries);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }


    @Test
     void whenSearchDeliveryStatusId_thenDeliveryStatusShouldBeFound() {
        Optional<DeliveryStatus> found = service.getDeliveryStatusById( 111L );
        DeliveryStatus deliveryStatus = null;
        if (found.isPresent()) deliveryStatus = found.get();

        assertThat(deliveryStatus.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenDeliveryStatusShouldNotBeFound() {
        Optional<DeliveryStatus> found = service.getDeliveryStatusById(-99L);
        DeliveryStatus deliveryStatus = null;
        if (found.isPresent()) deliveryStatus = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(deliveryStatus).isNull();
    }


    @Test
     void whengetAll_thenReturn5Records() {
        DeliveryStatus d1 = new DeliveryStatus(DeliveryStatusEnum.COLLECTING);
        DeliveryStatus d2 = new DeliveryStatus(DeliveryStatusEnum.CANCELLED);
        DeliveryStatus d3 = new DeliveryStatus(DeliveryStatusEnum.DELIVERED);
        DeliveryStatus d4 = new DeliveryStatus(DeliveryStatusEnum.QUEUED);
        DeliveryStatus d5 = new DeliveryStatus(DeliveryStatusEnum.DELIVERING);

        List<DeliveryStatus> allDeliveries = service.getAllDeliveries();     

        // verify if FindAllDeliveryStatus is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allDeliveries).hasSize(5).extracting(DeliveryStatus::getDescription).contains(d1.getDescription(), d2.getDescription(), d3.getDescription(), d4.getDescription(), d5.getDescription());
    }
 }