package com.tqs.project.repository;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.tqs.project.Exception.UserAlreadyAssignedException;
import com.tqs.project.Model.Business;
import com.tqs.project.Model.BusinessCourierInteractions;
import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.Model.Courier;
import com.tqs.project.Model.User;
import com.tqs.project.Repository.BusinessCourierInteractionsEventTypeRepository;
import com.tqs.project.Repository.BusinessCourierInteractionsRepository;
import com.tqs.project.Repository.BusinessRepository;
import com.tqs.project.Repository.CourierRepository;
import com.tqs.project.Repository.UserRepository;
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
    public static MySQLContainer container = new MySQLContainer()
        .withUsername("user")
        .withPassword("user")
        .withDatabaseName("tqs_41");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }
    @Test
    void contextLoads(){
        System.out.println("Context Loads!");
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
    private BusinessCourierInteractionsEventTypeRepository repBusinessEvent;
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateBusinessCourierInteractionsAndFindById_thenReturnSameBusinessCourierInteractions() throws UserAlreadyAssignedException {
        BusinessCourierInteractions x = new BusinessCourierInteractions();
        Courier c = new Courier();
        c.setName("alex");
        x.setCourier(c);
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        user.setCourier(null);
        Business b = new Business();
        BusinessCourierInteractionsEventType bus= new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        x.setEvent(bus);
        b.setUser(user);
        x.setBusiness(b);
        repUser.saveAndFlush(user);
        c.setUser(user);
        repCour.saveAndFlush(c);
        repBusiness.saveAndFlush(b);
        repBusinessEvent.saveAndFlush(bus);
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
    void testGivenBusinessCourierInteractionsAndFindByAll_thenReturnSameBusinessCourierInteractions() throws UserAlreadyAssignedException {
      

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
        user1.setUsername("aaaaa");
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        user.setCourier(null);
        Business b = new Business();
        Business b1 = new Business();

        BusinessCourierInteractionsEventType bus= new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        x.setEvent(bus);
        x2.setEvent(bus);


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
        repBusinessEvent.saveAndFlush(bus);
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
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
    
    

}
