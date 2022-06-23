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
import com.tqs.project.repository.CourierRepository;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CourierServiceTest {

    @Mock( lenient = true)
    private CourierRepository rep;

    @InjectMocks
    private CourierService service;

    @BeforeEach
    public void setUp() {
        Courier john = new Courier();
        john.setId(111L);
        john.setName("John");

        Courier andrew = new Courier();
        andrew.setName("Andrew");

        Courier george = new Courier();
        george.setName("George");

        List<Courier> allCouriers = Arrays.asList(john, andrew, george);

        Mockito.when(rep.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(rep.findAll()).thenReturn(allCouriers);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSaveCourier_thenReturnCourier() {
        Courier courier = new Courier();
        Mockito.when(rep.save(courier)).thenReturn(courier);

        assertThat(service.save(courier)).isEqualTo(courier);

        Mockito.verify(rep, VerificationModeFactory.times(1)).save(courier);
    }

    @Test
     void whenSearchCourierId_thenCourierShouldBeFound() {
        Optional<Courier> found = service.getCourierById( 111L );
        Courier c = null;
        if (found.isPresent()) c = found.get();

        assertThat(c.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenCourierShouldNotBeFound() {
        Optional<Courier> found = service.getCourierById(-99L);
        Courier c = null;
        if (found.isPresent()) c = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(c).isNull();
    }


    @Test
     void whengetAll_thenReturn3Records() {
        Courier john = new Courier();
        john.setName("John");
        Courier andrew = new Courier();
        andrew.setName("Andrew");
        Courier george = new Courier();
        george.setName("George");

        List<Courier> allCouriers = service.getAllCouriers();

        // verify if FindAllCouriers is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allCouriers).hasSize(3).extracting(Courier::getName).contains(andrew.getName(), john.getName(), george.getName());
    }
 }