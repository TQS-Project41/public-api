package com.tqs.project.repository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.tqs.project.model.Business;
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.model.Courier;
import com.tqs.project.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusinessCourierInteractionsRepositoryTest {
    
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private BusinessCourierInteractionsRepository rep;

    @Autowired
    private UserRepository repUser;

    @Autowired
    private BusinessRepository repBusiness;

    @Autowired
    private CourierRepository repCour;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateBusinessCourierInteractionsAndFindById_thenReturnSameBusinessCourierInteractions() {
        BusinessCourierInteractions x = new BusinessCourierInteractions();
        Courier c = new Courier();
        c.setName("alex");
        x.setCourier(c);
        User user = new User();
        user.setPassword("xxxx");
        user.setEmail("username");
        Business b = new Business();
        BusinessCourierInteractionsEventTypeEnum event = BusinessCourierInteractionsEventTypeEnum.ACCEPT;        
        x.setEvent(event);
        b.setUser(user);
        x.setBusiness(b);
        repUser.saveAndFlush(user);
        c.setUser(user);
        repCour.saveAndFlush(c);
        repBusiness.saveAndFlush(b);
        rep.saveAndFlush(x);
        Optional<BusinessCourierInteractions> res = rep.findById(x.getId());
        System.out.println(res.get().getTimestamp());
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<BusinessCourierInteractions> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */
    
    @Test
    void testGivenBusinessCourierInteractionsAndFindByAll_thenReturnSameBusinessCourierInteractions() {
      

        BusinessCourierInteractions x = new BusinessCourierInteractions();
        BusinessCourierInteractions x2 = new BusinessCourierInteractions();

        Courier c = new Courier();
        c.setName("alex");
        x.setCourier(c);
        Courier c1 = new Courier();
        x2.setCourier(c1);
        c1.setName("serras");
        User user1 = new User();
        user1.setPassword("xxxx");
        user1.setEmail("aaaaa");
        User user = new User();
        user.setPassword("xxxx");
        user.setEmail("username");
        Business b = new Business();
        Business b1 = new Business();

        BusinessCourierInteractionsEventTypeEnum event = BusinessCourierInteractionsEventTypeEnum.ACCEPT;        
        x.setEvent(event);
        x2.setEvent(event);

        b.setUser(user);
        b1.setUser(user1);

        x.setBusiness(b);
        x2.setBusiness(b1);

        repUser.saveAndFlush(user);
        repUser.saveAndFlush(user1);
        c.setUser(user);
        c1.setUser(user1);

        repCour.saveAndFlush(c);
        repCour.saveAndFlush(c1);

        repBusiness.saveAndFlush(b);
        repBusiness.saveAndFlush(b1);
        rep.saveAndFlush(x);
        rep.saveAndFlush(x2);



       
        


        List<BusinessCourierInteractions> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(BusinessCourierInteractions::getId)
                .contains(x.getId(), x2.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(BusinessCourierInteractions::getCourier)
                .contains(x.getCourier(), x2.getCourier());
    }

    @Test
    void testGivenNoBusinessCourierInteractions_whenFindAll_thenReturnEmpty() {
        List<BusinessCourierInteractions> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateBusinessCourierInteractions_thenReturnException() {
        BusinessCourierInteractions  x = new BusinessCourierInteractions();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

    @Test
    void whenFindLast_thenReturnOnlyLastAsOptional() {
        User userCourier = new User("email", "password");
        User userBusiness = new User("email2", "password");
        User userBusiness2 = new User("email3", "password");

        repUser.saveAllAndFlush(Arrays.asList(userCourier, userBusiness, userBusiness2));

        Courier courier = new Courier(userCourier, "name", "photo", LocalDate.of(2021, 10, 12));
        Business business = new Business(userBusiness);
        Business business2 = new Business(userBusiness2);

        repCour.saveAndFlush(courier);
        repBusiness.saveAndFlush(business);
        repBusiness.saveAndFlush(business2);

        Optional<BusinessCourierInteractions> last = rep.findFirstByBusinessAndCourierOrderByTimestampDesc(business,
                courier);
        assertThat(last).isNotPresent();

        BusinessCourierInteractions it1 = new BusinessCourierInteractions(business, courier,
                BusinessCourierInteractionsEventTypeEnum.APPLY);
        rep.saveAndFlush(it1);

        last = rep.findFirstByBusinessAndCourierOrderByTimestampDesc(business, courier);
        assertThat(last).isPresent();
        assertThat(last.get().getEvent()).isEqualTo(BusinessCourierInteractionsEventTypeEnum.APPLY);

        BusinessCourierInteractions it2 = new BusinessCourierInteractions(business, courier,
                BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        rep.saveAndFlush(it2);

        last = rep.findFirstByBusinessAndCourierOrderByTimestampDesc(business, courier);
        assertThat(last).isPresent();
        assertThat(last.get().getEvent()).isEqualTo(BusinessCourierInteractionsEventTypeEnum.ACCEPT);

        last = rep.findFirstByBusinessAndCourierOrderByTimestampDesc(business2, courier);
        assertThat(last).isNotPresent();
    }

    @Test
    void whenFindByBusinessAndCourier_thenReturnOnlyInteractionsBetweenTheTwo() {
        User userCourier = new User("email", "password");
        User userBusiness = new User("email2", "password");
        User userBusiness2 = new User("email3", "password");

        repUser.saveAllAndFlush(Arrays.asList(userCourier, userBusiness, userBusiness2));

        Courier courier = new Courier(userCourier, "name", "photo", LocalDate.of(2021, 10, 12));
        Business business = new Business(userBusiness);
        Business business2 = new Business(userBusiness2);

        repCour.saveAndFlush(courier);
        repBusiness.saveAndFlush(business);
        repBusiness.saveAndFlush(business2);

        List<BusinessCourierInteractions> interactions = rep.findByBusinessAndCourierOrderByTimestampDesc(business, courier);
        assertThat(interactions).hasSize(0);

        BusinessCourierInteractions it1 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        rep.saveAndFlush(it1);

        interactions = rep.findByBusinessAndCourierOrderByTimestampDesc(business, courier);
        assertThat(interactions).hasSize(1);

        BusinessCourierInteractions it2 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        rep.saveAndFlush(it2);

        interactions = rep.findByBusinessAndCourierOrderByTimestampDesc(business, courier);
        assertThat(interactions).hasSize(2);

        interactions = rep.findByBusinessAndCourierOrderByTimestampDesc(business2, courier);
        assertThat(interactions).hasSize(0);
    } 

    @Test
    void whenFindBusinessesByCourierAndEvent_thenReturnOnlyInteractionsBetweenTheTwo() {
        User userCourier = new User("email", "password");
        User userCourier2 = new User("email2", "password");
        User userBusiness = new User("email3", "password");
        User userBusiness2 = new User("email4", "password");

        repUser.saveAllAndFlush(Arrays.asList(userCourier, userCourier2, userBusiness, userBusiness2));

        Courier courier = new Courier(userCourier, "name", "photo", LocalDate.of(2021, 10, 12));
        Courier courier2 = new Courier(userCourier2, "name", "photo", LocalDate.of(2021, 10, 12));
        Business business = new Business(userBusiness);
        Business business2 = new Business(userBusiness2);

        repCour.saveAndFlush(courier);
        repCour.saveAndFlush(courier2);
        repBusiness.saveAndFlush(business);
        repBusiness.saveAndFlush(business2);

        List<BusinessCourierInteractions> interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(0);

        BusinessCourierInteractions it1 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        rep.saveAndFlush(it1);

        interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);

        BusinessCourierInteractions it2 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        rep.saveAndFlush(it2);

        interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);

        BusinessCourierInteractions it3 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        rep.saveAndFlush(it3);

        interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);

        interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier2, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(0);


        BusinessCourierInteractions it4 = new BusinessCourierInteractions(business2, courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        rep.saveAndFlush(it4);

        interactions = rep.findDistinctBusinessByCourierAndEventOrderByTimestampDesc(courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(2);
    } 

    @Test
    void whenFindCourierByBusinessAndEvent_thenReturnOnlyInteractionsBetweenTheTwo() {
        User userCourier = new User("email", "password");
        User userBusiness = new User("email3", "password");
        User userBusiness2 = new User("email4", "password");

        repUser.saveAllAndFlush(Arrays.asList(userCourier, userBusiness, userBusiness2));

        Courier courier = new Courier(userCourier, "name", "photo", LocalDate.of(2021, 10, 12));
        Business business = new Business(userBusiness);
        Business business2 = new Business(userBusiness2);

        repCour.saveAndFlush(courier);
        repBusiness.saveAndFlush(business);
        repBusiness.saveAndFlush(business2);

        List<BusinessCourierInteractions> interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(0);

        BusinessCourierInteractions it1 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        rep.saveAndFlush(it1);

        interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);

        BusinessCourierInteractions it2 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        rep.saveAndFlush(it2);

        interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);

        BusinessCourierInteractions it3 = new BusinessCourierInteractions(business, courier, BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        rep.saveAndFlush(it3);

        interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);

        interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business2, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(0);

        BusinessCourierInteractions it4 = new BusinessCourierInteractions(business2, courier, BusinessCourierInteractionsEventTypeEnum.APPLY);
        rep.saveAndFlush(it4);

        interactions = rep.findDistinctCourierByBusinessAndEventOrderByTimestampDesc(business2, BusinessCourierInteractionsEventTypeEnum.APPLY);
        assertThat(interactions).hasSize(1);
    } 

}
