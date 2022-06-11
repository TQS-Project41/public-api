package com.tqs.project.repository;


import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.tqs.project.model.BusinessCourierInteractionsEventType;
import com.tqs.project.model.BusinessCourierInteractionsEventTypeEnum;

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
public class BusinessCourierInteractionsEventTypeRepositoryTest {

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private BusinessCourierInteractionsEventTypeRepository rep;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateBusinessCourierInteractionsEventTypeAndFindById_thenReturnSameBusinessCourierInteractionsEventType() {
        BusinessCourierInteractionsEventType x = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        entityManager.persistAndFlush(x);
        Optional<BusinessCourierInteractionsEventType> res = rep.findById(x.getId());
        
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<BusinessCourierInteractionsEventType> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenBusinessCourierInteractionsEventTypeAndFindByAll_thenReturnSameBusinessCourierInteractionsEventType() {
        BusinessCourierInteractionsEventType  b1 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        BusinessCourierInteractionsEventType  b2 = new BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum.ACCEPT);
        
        entityManager.persistAndFlush(b1);
        entityManager.persistAndFlush(b2);


        List<BusinessCourierInteractionsEventType> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(BusinessCourierInteractionsEventType::getId)
                .contains(b1.getId(), b2.getId());
    }

    @Test
    void testGivenNoBusinessCourierInteractionsEventType_whenFindAll_thenReturnEmpty() {
        List<BusinessCourierInteractionsEventType> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidBusinessCourierInteractionsEventTypeAndFindById_thenReturnException() {
        BusinessCourierInteractionsEventType  x = new BusinessCourierInteractionsEventType();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
