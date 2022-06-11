package com.tqs.project.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

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
public class CourierRepositoryTest {
    
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

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
        user.setEmail("username");
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
        user.setEmail("username");
        x.setUser(user);
        repUser.saveAndFlush(user);
        Courier x1 = new Courier();
        User user1 = new User();
        x.setName("SERRAS");
        x1.setName("Alex");
        user1.setPassword("xxxx");
        user1.setEmail("aaaa");
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
