package com.tqs.project.repository;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.tqs.project.Exception.BadLocationException;
import com.tqs.project.Model.Address;
import com.tqs.project.Model.Business;
import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.Model.DeliveryStatus;
import com.tqs.project.Model.DeliveryStatusEnum;
import com.tqs.project.Model.Shop;
import com.tqs.project.Model.User;
import com.tqs.project.Repository.BusinessRepository;
import com.tqs.project.Repository.DeliveryStatusRepository;
import com.tqs.project.Repository.ShopRepository;
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
public class DeliveryStatusRepositoryTest {
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
    private TestEntityManager entityManager;

    

    @Autowired
    private DeliveryStatusRepository rep;

    @Test
    void testWhenCreateDeliveryStatusAndFindById_thenReturnSameDeliveryStatus() throws BadLocationException {
        DeliveryStatus b1 = new DeliveryStatus();
        b1.setDescription(DeliveryStatusEnum.QUEUED);
        entityManager.persistAndFlush(b1);
        Optional<DeliveryStatus> res = rep.findById(b1.getId());
        
        assertThat(res).isPresent().contains(b1);
    }

    

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<DeliveryStatus> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }

    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenDeliveryStatusAndFindByAll_thenReturnSameDeliveryStatus() {
        DeliveryStatus b1 = new DeliveryStatus();
        b1.setDescription(DeliveryStatusEnum.QUEUED);
        entityManager.persistAndFlush(b1);
        
        DeliveryStatus b2 = new DeliveryStatus();
        b2.setDescription(DeliveryStatusEnum.QUEUED);
        entityManager.persistAndFlush(b2);


        List<DeliveryStatus> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(DeliveryStatus::getId)
                .contains(b1.getId(), b2.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(DeliveryStatus::getDescription)
                .contains(b1.getDescription(), b2.getDescription());
    }

    @Test
    void testGivenNoDeliveryStatus_whenFindAll_thenReturnEmpty() {
        List<DeliveryStatus> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateDeliveryStatus_thenReturnException() {
        DeliveryStatus  x = new DeliveryStatus();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
