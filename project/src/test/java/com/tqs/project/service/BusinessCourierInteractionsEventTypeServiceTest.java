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

import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.Repository.BusinessCourierInteractionsEventTypeRepository;
import com.tqs.project.Service.BusinessCourierInteractionsEventTypeService;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
 class BusinessCourierInteractionsEventTypeServiceTest {

    @Mock( lenient = true)
    private BusinessCourierInteractionsEventTypeRepository rep;

    @InjectMocks
    private BusinessCourierInteractionsEventTypeService service;

    @BeforeEach
    public void setUp() {
        BusinessCourierInteractionsEventType b1 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        b1.setId(111L);

        BusinessCourierInteractionsEventType b2 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.APPLY);
        BusinessCourierInteractionsEventType b3 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.BLOCK);

        List<BusinessCourierInteractionsEventType> allBusinessCourierInteractionsEventTypes = Arrays.asList(b1, b2, b3);

        Mockito.when(rep.findById(b1.getId())).thenReturn(Optional.of(b1));
        Mockito.when(rep.findAll()).thenReturn(allBusinessCourierInteractionsEventTypes);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }


    @Test
     void whenSearchBusinessCourierInteractionsEventTypeId_thenBusinessCourierInteractionsEventTypeShouldBeFound() {
        Optional<BusinessCourierInteractionsEventType> found = service.getBusinessCourierInteractionsEventTypeById( 111L );
        BusinessCourierInteractionsEventType b = null;
        if (found.isPresent()) b = found.get();

        assertThat(b.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenBusinessCourierInteractionsEventTypeShouldNotBeFound() {
        Optional<BusinessCourierInteractionsEventType> found = service.getBusinessCourierInteractionsEventTypeById(-99L);
        BusinessCourierInteractionsEventType b = null;
        if (found.isPresent()) b = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(b).isNull();
    }


    @Test
     void whengetAll_thenReturn4Records() {
        BusinessCourierInteractionsEventType b1 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        BusinessCourierInteractionsEventType b2 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.APPLY);
        BusinessCourierInteractionsEventType b3 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.BLOCK);

        List<BusinessCourierInteractionsEventType> allBusinessCourierInteractionsEventTypes = service.getAllBusinessCourierInteractionsEventTypes();

        // verify if FindAllBusinessCourierInteractionsEventTypes is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allBusinessCourierInteractionsEventTypes).hasSize(3).extracting(BusinessCourierInteractionsEventType::getDescription).contains(b1.getDescription(), b2.getDescription(), b3.getDescription());
    }
 }