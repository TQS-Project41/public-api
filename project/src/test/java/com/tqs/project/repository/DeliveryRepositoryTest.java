package com.tqs.project.repository;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.tqs.project.exception.BadLocationException;
import com.tqs.project.exception.BadPhoneNumberException;
import com.tqs.project.model.Address;
import com.tqs.project.model.Business;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.DeliveryContact;
import com.tqs.project.model.Shop;
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
public class DeliveryRepositoryTest {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private DeliveryRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repUser;

    @Test
    void testWhenCreateDeliveryAndFindById_thenReturnSameDelivery() throws BadLocationException, BadPhoneNumberException {

        Business b1 = new Business();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        b1.setUser(user);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(b1);

        Shop x = new Shop();
        x.setName("Continente");
        x.setAddress(new Address(50.0,-50.0));
        x.setBusiness(b1);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(x);

        Courier cor = new Courier();
        User user1 = new User();
        user1.setPassword("xxxx");
        user1.setUsername("aaaa");
        cor.setUser(user);
        cor.setName("SERRAS");
        repUser.saveAndFlush(user1);
        entityManager.persistAndFlush(cor);

        Delivery del = new Delivery();
        del.setShop(x);
        del.setCourier(cor);
        del.setDeliveryAddress(new Address(30.0,-100.0));
        del.setClient(new DeliveryContact("serras", "912321123"));
        entityManager.persistAndFlush(del);
        Optional<Delivery> res = rep.findById(del.getId());
        
        assertThat(res).isPresent().contains(del);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Delivery> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenDeliveryAndFindByAll_thenReturnSameDelivery() throws BadLocationException, BadPhoneNumberException {
        
        Business b1 = new Business();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        b1.setUser(user);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(b1);

        Shop x = new Shop();
        x.setName("Continente");
        x.setAddress(new Address(50.0,-50.0));
        x.setBusiness(b1);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(x);

        Courier cor = new Courier();
        User user1 = new User();
        user1.setPassword("xxxx");
        user1.setUsername("aaaa");
        cor.setUser(user);
        cor.setName("SERRAS");
        repUser.saveAndFlush(user1);
        entityManager.persistAndFlush(cor);

        Delivery del = new Delivery();
        del.setShop(x);
        del.setCourier(cor);
        del.setDeliveryAddress(new Address(30.0,-100.0));
        del.setClient(new DeliveryContact("serras", "912321123"));
        entityManager.persistAndFlush(del);

        Delivery del1 = new Delivery();
        del1.setCourier(cor);
        del1.setShop(x);
        del1.setDeliveryAddress(new Address(40.0,-100.0));
        del1.setClient(new DeliveryContact("serras", "912321323"));
        entityManager.persistAndFlush(del1);


        List<Delivery> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Delivery::getId)
                .contains(del.getId(), del1.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(Delivery::getClient)
                .contains(del.getClient(), del1.getClient());
        
    }

    @Test
    void testGivenNoDelivery_whenFindAll_thenReturnEmpty() {
        List<Delivery> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateDelivery_thenReturnException() {
        Delivery  x = new Delivery();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
