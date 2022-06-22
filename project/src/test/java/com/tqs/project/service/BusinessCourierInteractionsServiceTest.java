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
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.model.Courier;
import com.tqs.project.repository.BusinessCourierInteractionsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


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

        Mockito.when(rep.findById(111L)).thenReturn(Optional.of(b1));
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSaveInteraction_thenReturnInteraction() {
        BusinessCourierInteractions interaction = new BusinessCourierInteractions();
        Mockito.when(rep.save(interaction)).thenReturn(interaction);

        assertThat(service.save(interaction)).isEqualTo(interaction);

        Mockito.verify(rep, VerificationModeFactory.times(1)).save(interaction);
    }

    @Test
    void whenSearchBusinessCourierInteractionsId_thenBusinessCourierInteractionsShouldBeFound() {
        Optional<BusinessCourierInteractions> found = service.getById(111L);
        BusinessCourierInteractions b = null;
        if (found.isPresent())
            b = found.get();

        assertThat(b.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchInvalidId_thenBusinessCourierInteractionsShouldNotBeFound() {
        Optional<BusinessCourierInteractions> found = service.getById(-99L);
        BusinessCourierInteractions b = null;
        if (found.isPresent())
            b = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(b).isNull();
    }

    @Test
    void whenSearchLastInteraction_thenReturnValidOptional() {
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.empty());

        Optional<BusinessCourierInteractions> found = service.getLastInteraction(null, null);

        assertThat(found).isNotPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
    }

    @Test
    void whenSearchAll_thenReturnValidList() {
        Mockito.when(rep.findByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Arrays.asList());

        List<BusinessCourierInteractions> found = service.getAll(null, null);

        assertThat(found).isEmpty();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findByBusinessAndCourierOrderByTimestampDesc(null, null);
    }

    @Test
    void whenSearchAllActiveByCourier_thenReturnOnlyValid() {
        Business b1 = new Business();
        Business b2 = new Business();

        Courier c1 = new Courier();

        BusinessCourierInteractions it1 = new BusinessCourierInteractions(b1, c1, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        BusinessCourierInteractions it2 = new BusinessCourierInteractions(b1, c1, BusinessCourierInteractionsEventTypeEnum.BLOCK);
        BusinessCourierInteractions it3 = new BusinessCourierInteractions(b2, c1, BusinessCourierInteractionsEventTypeEnum.ACCEPT);

        it1.setId(1);
        it2.setId(2);
        it3.setId(3);

        Mockito.when(rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(c1, BusinessCourierInteractionsEventTypeEnum.ACCEPT)).thenReturn(Arrays.asList(it1, it3));
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(b1, c1)).thenReturn(Optional.of(it2));
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(b2, c1)).thenReturn(Optional.of(it3));

        List<BusinessCourierInteractions> found = service.getAllActive(c1);

        assertThat(found).hasSize(1);

        Mockito.verify(rep, VerificationModeFactory.times(1)).findDistinctBusinessByCourierAndEventOrderByTimestampDesc(c1, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(b1, c1);
        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(b2, c1);
    }

    @Test
    void whenSearchAllActiveByBusiness_thenReturnOnlyValid() {
        Business b1 = new Business();

        Courier c1 = new Courier();
        Courier c2 = new Courier();

        BusinessCourierInteractions it1 = new BusinessCourierInteractions(b1, c1, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        BusinessCourierInteractions it2 = new BusinessCourierInteractions(b1, c1, BusinessCourierInteractionsEventTypeEnum.BLOCK);
        BusinessCourierInteractions it3 = new BusinessCourierInteractions(b1, c2, BusinessCourierInteractionsEventTypeEnum.ACCEPT);

        it1.setId(1);
        it2.setId(2);
        it3.setId(3);

        Mockito.when(rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(b1, BusinessCourierInteractionsEventTypeEnum.ACCEPT)).thenReturn(Arrays.asList(it1, it3));
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(b1, c1)).thenReturn(Optional.of(it2));
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(b1, c2)).thenReturn(Optional.of(it3));

        List<BusinessCourierInteractions> found = service.getAllActive(b1);

        assertThat(found).hasSize(1);

        Mockito.verify(rep, VerificationModeFactory.times(1)).findDistinctCourierByBusinessAndEventOrderByTimestampDesc(b1, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(b1, c1);
        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(b1, c2);
    }

    @Test
    void givenACourierHasBeenBlocked_whenCourierTriesToApply_thenReturnsNotPresent() {

        BusinessCourierInteractions it = new BusinessCourierInteractions(null, null, BusinessCourierInteractionsEventTypeEnum.BLOCK);
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.of(it));

        Optional<BusinessCourierInteractions> interaction = service.apply(null, null);
        assertThat(interaction).isNotPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(0)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierHasNotBeenBlocked_whenCourierTriesToApply_thenReturnsPresent() {

        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.empty());
        Mockito.when(rep.save(any(BusinessCourierInteractions.class))).thenReturn(new BusinessCourierInteractions());

        Optional<BusinessCourierInteractions> interaction = service.apply(null, null);
        assertThat(interaction).isPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(1)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierIsNotAccepted_whenCourierTriesToRefuse_thenReturnsNotPresent() {

        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.empty());

        Optional<BusinessCourierInteractions> interaction = service.refuse(null, null);
        assertThat(interaction).isNotPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(0)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierIsAccepted_whenCourierTriesToRefuse_thenReturnsPresent() {

        BusinessCourierInteractions it = new BusinessCourierInteractions(null, null, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.of(it));
        Mockito.when(rep.save(any(BusinessCourierInteractions.class))).thenReturn(new BusinessCourierInteractions());

        Optional<BusinessCourierInteractions> interaction = service.refuse(null, null);
        assertThat(interaction).isPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(1)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierHasNotApplied_whenBusinessTriesToAccept_thenReturnsNotPresent() {

        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.empty());

        Optional<BusinessCourierInteractions> interaction = service.accept(null, null);
        assertThat(interaction).isNotPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(0)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierHasApplied_whenBusinessTriesToAccept_thenReturnsPresent() {

        BusinessCourierInteractions it = new BusinessCourierInteractions(null, null, BusinessCourierInteractionsEventTypeEnum.APPLY);
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.of(it));
        Mockito.when(rep.save(any(BusinessCourierInteractions.class))).thenReturn(new BusinessCourierInteractions());

        Optional<BusinessCourierInteractions> interaction = service.accept(null, null);
        assertThat(interaction).isPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(1)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierIsNotAccepted_whenBusinessTriesToBlock_thenReturnsNotPresent() {

        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.empty());

        Optional<BusinessCourierInteractions> interaction = service.block(null, null);
        assertThat(interaction).isNotPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(0)).save(any(BusinessCourierInteractions.class));

    }

    @Test
    void givenACourierIsAccepted_whenBusinessTriesToBlock_thenReturnsPresent() {

        BusinessCourierInteractions it = new BusinessCourierInteractions(null, null, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        Mockito.when(rep.findFirstByBusinessAndCourierOrderByTimestampDesc(null, null)).thenReturn(Optional.of(it));
        Mockito.when(rep.save(any(BusinessCourierInteractions.class))).thenReturn(new BusinessCourierInteractions());

        Optional<BusinessCourierInteractions> interaction = service.block(null, null);
        assertThat(interaction).isPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findFirstByBusinessAndCourierOrderByTimestampDesc(null, null);
        Mockito.verify(rep, VerificationModeFactory.times(1)).save(any(BusinessCourierInteractions.class));

    }
}