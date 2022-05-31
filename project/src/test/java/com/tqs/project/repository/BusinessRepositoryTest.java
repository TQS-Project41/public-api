package com.tqs.project.repository;



import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.tqs.project.Model.Business;
import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.Model.User;
import com.tqs.project.Repository.BusinessRepository;
import com.tqs.project.Repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import org.apache.commons.codec.binary.Base16;
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
public class BusinessRepositoryTest {
    
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
    private BusinessRepository rep;

    @Autowired
    private UserRepository repUser;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateBusinessAndFindById_thenReturnSameBusiness() {
        Business x = new Business();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        x.setUser(user);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(x);
        Optional<Business> res = rep.findById(x.getId());
        
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Business> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenBusinessAndFindByAll_thenReturnSameBusiness() {
        
        Business b1 = new Business();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        b1.setUser(user);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(b1);

        Business b2 = new Business();
        User user1 = new User();
        user1.setPassword("xxxx");
        user1.setUsername("serras");
        b2.setUser(user1);
        repUser.saveAndFlush(user1);
        entityManager.persistAndFlush(b2);

        List<Business> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Business::getId)
                .contains(b1.getId(), b2.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(Business::getUser)
                .contains(b1.getUser(), b2.getUser());
        
    }

    @Test
    void testGivenNoBusiness_whenFindAll_thenReturnEmpty() {
        List<Business> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateBusiness_thenReturnException() {
        Business  x = new Business();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

}
