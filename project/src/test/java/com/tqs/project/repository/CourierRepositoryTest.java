package com.tqs.project.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.Model.Courier;
import com.tqs.project.Model.User;
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
public class CourierRepositoryTest {
    
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

    @Autowired
    private CourierRepository rep;

    @Autowired
    private UserRepository repUser;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateCourierAndFindById_thenReturnSameCourier() {
        Courier x = new Courier();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        x.setUser(user);
        x.setName("SERRAS");
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(x);
        Optional<Courier> res = rep.findById(x.getId());
        
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Courier> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenCourierAndFindByAll_thenReturnSameCourier() {
        Courier x = new Courier();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        x.setUser(user);
        repUser.saveAndFlush(user);
        Courier x1 = new Courier();
        User user1 = new User();
        x.setName("SERRAS");
        x1.setName("Alex");
        user1.setPassword("xxxx");
        user1.setUsername("aaaa");
        x1.setUser(user1);
        repUser.saveAndFlush(user1);

        entityManager.persistAndFlush(x);
        entityManager.persistAndFlush(x1);



        List<Courier> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Courier::getId)
                .contains(x.getId(), x1.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(Courier::getName)
                .contains(x.getName(), x1.getName());
    }

    @Test
    void testGivenNoCourier_whenFindAll_thenReturnEmpty() {
        List<Courier> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateCourier_thenReturnException() {
        Courier  x = new Courier();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }


}
