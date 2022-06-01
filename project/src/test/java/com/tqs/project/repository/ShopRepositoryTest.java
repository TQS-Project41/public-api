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
import com.tqs.project.Model.Shop;
import com.tqs.project.Model.User;
import com.tqs.project.Repository.BusinessRepository;
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
public class ShopRepositoryTest {
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
    private ShopRepository rep;

    @Autowired
    private UserRepository repUser;
    @Autowired
    private BusinessRepository repBusi;
    @Test
    void testWhenCreateShopAndFindById_thenReturnSameShop() throws BadLocationException {
        Business b1 = new Business();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        b1.setUser(user);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(b1);

        Shop x = new Shop();
        x.setName("Continente");
        x.setShop_address(new Address(50.0,-50.0));
        x.setBusiness(b1);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(x);
        Optional<Shop> res = rep.findById(x.getId());
        
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Shop> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }



    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenShopAndFindByAll_thenReturnSameShop() throws BadLocationException {
        
        Business b1 = new Business();
        User user = new User();
        user.setPassword("xxxx");
        user.setUsername("username");
        b1.setUser(user);
        repUser.saveAndFlush(user);
        entityManager.persistAndFlush(b1);

        Shop x = new Shop();
        x.setName("Continente");
        x.setShop_address(new Address(50.0,-50.0));
        x.setBusiness(b1);
        Shop x1 = new Shop();
        x1.setName("Continente");
        x1.setShop_address(new Address(80.0,-110.0));
        x1.setBusiness(b1);
        entityManager.persistAndFlush(x);
        entityManager.persistAndFlush(x1);


        List<Shop> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Shop::getId)
                .contains(x.getId(), x1.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(Shop::getBusiness)
                .contains(x.getBusiness(), x1.getBusiness());
        
    }

    @Test
    void testGivenNoShop_whenFindAll_thenReturnEmpty() {
        List<Shop> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateShop_thenReturnException() {
        Shop  x = new Shop();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
    
}
