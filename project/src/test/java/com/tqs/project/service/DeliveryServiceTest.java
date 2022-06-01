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

import com.tqs.project.Model.Courier;
import com.tqs.project.Model.Delivery;
import com.tqs.project.Repository.DeliveryRepository;
import com.tqs.project.Service.DeliveryService;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
 class DeliveryServiceTest {

    @Mock( lenient = true)
    private DeliveryRepository rep;

    @InjectMocks
    private DeliveryService service;

    @BeforeEach
    public void setUp() {

        Delivery d1 = new Delivery();
        d1.setId(111L);

        Delivery d2 = new Delivery();
        d2.setId(111L);

        Courier c = new Courier();
        c.setName("António");
        d1.setCourier(c);
        d2.setCourier(c);

        List<Delivery> allDeliveries = Arrays.asList(d1, d2);

        Mockito.when(rep.findById(d1.getId())).thenReturn(Optional.of(d1));
        Mockito.when(rep.findAll()).thenReturn(allDeliveries);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }


    @Test
     void whenSearchDeliveryId_thenDeliveryShouldBeFound() {
        Optional<Delivery> found = service.getDeliveryById( 111L );
        Delivery delivery = null;
        if (found.isPresent()) delivery = found.get();

        assertThat(delivery.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenDeliveryShouldNotBeFound() {
        Optional<Delivery> found = service.getDeliveryById(-99L);
        Delivery delivery = null;
        if (found.isPresent()) delivery = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(delivery).isNull();
    }


    @Test
     void whengetAll_thenReturn2Records() {
        Delivery d1 = new Delivery();
        d1.setId(111L);

        Delivery d2 = new Delivery();
        d2.setId(111L);

        Courier c = new Courier();
        c.setName("António");
        d1.setCourier(c);
        d2.setCourier(c);

        List<Delivery> allDeliveries = service.getAllDeliveries();

        // verify if FindAllDeliverys is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allDeliveries).hasSize(2).extracting(Delivery::getCourier).contains(d1.getCourier(), d2.getCourier());
    }
 }