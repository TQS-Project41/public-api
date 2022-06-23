package com.tqs.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.PageLoadStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.model.Shop;
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
        c.setId(1L);
        c.setName("António");
        Shop shop= new Shop();
        shop.setName("Puma");
        shop.setId(1);
        d1.setShop(shop);
        d1.setCourier(c);
        d2.setCourier(c);
        d1.setStatus(DeliveryStatusEnum.DELIVERING);
        d1.setStatus(DeliveryStatusEnum.QUEUED);
        Pageable pageable = Pageable.unpaged();

        List<Delivery> allDeliveries = Arrays.asList(d1, d2);

        Mockito.when(rep.findById(d1.getId())).thenReturn(Optional.of(d1));
        Mockito.when(rep.findByStatus(DeliveryStatusEnum.QUEUED,pageable)).thenReturn(
            new PageImpl<>(Arrays.asList(d2), pageable, 1));
        Mockito.when(rep.findAll(pageable)).thenReturn(
            new PageImpl<>(Arrays.asList(d1,d2), pageable, 2));
        Mockito.when(rep.findByShopId(1L,pageable)).thenReturn(
            new PageImpl<>(Arrays.asList(d1), pageable, 1));
        Mockito.when(rep.findByCourierId(1L,pageable)).thenReturn(
            new PageImpl<>(Arrays.asList(d1,d2), pageable, 2));
        Mockito.when(rep.findByCourierIdAndShopId(1L,1l,pageable)).thenReturn(
            new PageImpl<>(Arrays.asList(d1), pageable, 1));
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


    @Test
     void whenDeliveriesWithoutCourier_ThenReturnDeliveriesInQueue() {
        Delivery d1 = new Delivery();
        d1.setId(111L);
        Delivery d2 = new Delivery();
        d2.setId(111L);
        d2.setStatus(DeliveryStatusEnum.QUEUED);
        Pageable pageable = Pageable.unpaged();
        Page<Delivery> allDeliveries = service.deliveriesWithoutCourier(pageable);

        // verify if FindAllDeliverys is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findByStatus(DeliveryStatusEnum.QUEUED,pageable);

        assertThat(allDeliveries).hasSize(1).extracting(Delivery::getStatus).contains(d2.getStatus());
    }

    @Test
     void whengetAllWithouShopAndCourier_ThenReturnDeliveries() {
        Delivery d1 = new Delivery();
        d1.setId(111L);
        Delivery d2 = new Delivery();
        d2.setId(111L);
        d2.setStatus(DeliveryStatusEnum.QUEUED);
        Pageable pageable = Pageable.unpaged();
        Page<Delivery> allDeliveries = service.getAll(null,null,pageable);

        // verify if FindAllDeliverys is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll(pageable);

        assertThat(allDeliveries).hasSize(2).extracting(Delivery::getId).contains(d1.getId(),d2.getId());
    }


    @Test
     void whengetAllWithShopAndWithoutCourier_ThenReturnDeliveries() {
        Delivery d1 = new Delivery();
        d1.setId(111L);
        Delivery d2 = new Delivery();
        d2.setId(111L);
        d2.setStatus(DeliveryStatusEnum.QUEUED);
        Pageable pageable = Pageable.unpaged();
        Page<Delivery> allDeliveries = service.getAll(null,1L,pageable);

        // verify if FindAllDeliverys is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findByShopId(1L,pageable);

        assertThat(allDeliveries).hasSize(1).extracting(Delivery::getId).contains(d1.getId());
    }

    @Test
     void whengetAllWithhoutShopAndCourier_ThenReturnDeliveries() {
        Delivery d1 = new Delivery();
        d1.setId(111L);
        Delivery d2 = new Delivery();
        d2.setId(111L);
        d2.setStatus(DeliveryStatusEnum.QUEUED);
        Pageable pageable = Pageable.unpaged();
        Page<Delivery> allDeliveries = service.getAll(1L,null,pageable);

        // verify if FindAllDeliverys is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findByCourierId(1L,pageable);

        assertThat(allDeliveries).hasSize(2).extracting(Delivery::getId).contains(d1.getId(),d2.getId());
    }


    @Test
    void whengetAllWithShopAndCourier_ThenReturnDeliveries() {
       Delivery d1 = new Delivery();
       d1.setId(111L);
       Delivery d2 = new Delivery();
       d2.setId(111L);
       d2.setStatus(DeliveryStatusEnum.QUEUED);
       Pageable pageable = Pageable.unpaged();
       Page<Delivery> allDeliveries = service.getAll(1L,1L,pageable);

       // verify if FindAllDeliverys is called once
       Mockito.verify(rep, VerificationModeFactory.times(1)).findByCourierIdAndShopId(1l, 1l, pageable);

       assertThat(allDeliveries).hasSize(1).extracting(Delivery::getId).contains(d1.getId());
   }
 }