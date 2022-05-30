package com.tqs.project.repository;


import java.util.Optional;

import com.tqs.project.Model.BusinessCourierInteractionsEventType;
import com.tqs.project.Model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.Repository.BusinessCourierInteractionsEventTypeRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BusinessCourierInteractionsEventTypeRepositoryTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
        .withUsername("test")
        .withPassword("test")
        .withDatabaseName("test");

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

    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */
}
