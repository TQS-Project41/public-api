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

import com.tqs.project.model.Business;
import com.tqs.project.model.User;
import com.tqs.project.repository.BusinessRepository;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
 class BusinessServiceTest {

    @Mock( lenient = true)
    private BusinessRepository rep;

    @InjectMocks
    private BusinessService service;

    @BeforeEach
    public void setUp() {
        Business b1 = new Business();
        b1.setId(111L);
        b1.setUser( new User("123beer456", "jonikings") );

        Business b2 = new Business();
        b2.setId( 222L );
        b2.setUser( new User("noknok", "Alex") );

        List<Business> allBusiness = Arrays.asList(b1, b2);

        Mockito.when(rep.findById(b1.getId())).thenReturn(Optional.of(b1));
        Mockito.when(rep.findAll()).thenReturn(allBusiness);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSaveBusiness_thenReturnBusiness() {
        Business business = new Business();
        Mockito.when(rep.save(business)).thenReturn(business);

        assertThat(rep.save(business)).isEqualTo(business);

        Mockito.verify(rep, VerificationModeFactory.times(1)).save(business);
    }


    @Test
     void whenSearchBusinessId_thenBusinessShouldBeFound() {
        Optional<Business> found = service.getBusinessById( 111L );
        Business b = null;
        if (found.isPresent()) b = found.get();

        assertThat(b.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenBusinessShouldNotBeFound() {
        Optional<Business> found = service.getBusinessById(-99L);
        Business b = null;
        if (found.isPresent()) b = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(b).isNull();
    }


    @Test
     void whengetAll_thenReturn2Records() {
        Business b1 = new Business();
        b1.setId( 111L );

        Business b2 = new Business();
        b2.setId( 222L );

        List<Business> allBusinesss = service.getAllBusinesss();

        // verify if FindAllBusinesss is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allBusinesss).hasSize(2).extracting(Business::getId).contains(b1.getId(), b2.getId());
    }
 }