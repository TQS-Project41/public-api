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

import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.repository.DeliveryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyFloat;


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
    void whenSaveCourier_thenReturnCourier() {
        Delivery delivery = new Delivery();
        Mockito.when(rep.save(delivery)).thenReturn(delivery);

        assertThat(service.save(delivery)).isEqualTo(delivery);

        Mockito.verify(rep, VerificationModeFactory.times(1)).save(delivery);
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
     void test_fee_Function() {
        assertThat(service.getFee()).isEqualTo(5.0);
    }


    @Test
     void whengetAll_thenReturn2Records() {
        Delivery d1 = new Delivery();
        d1.setId(111L);

        Delivery d2 = new Delivery();
        d2.setId(111L);

        List<Delivery> allDeliveries = service.getAllDeliveries();

        // verify if FindAllDeliverys is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allDeliveries).hasSize(2).extracting(Delivery::getId).contains(d1.getId(), d2.getId());
    }
 }