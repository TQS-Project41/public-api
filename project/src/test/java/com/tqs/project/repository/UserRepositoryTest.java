
package com.tqs.project.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

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
public class UserRepositoryTest {

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private UserRepository rep;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateUserAndFindById_thenReturnSameUser() {
        User x = new User();
        x.setPassword("xxxx");
        x.setEmail("username");

        entityManager.persistAndFlush(x);
        Optional<User> res = rep.findById(x.getId());
        
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<User> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenUserAndFindByAll_thenReturnSameUser() {
        User  b1 = new User();
        User  b2 = new User();
        b1.setPassword("xxxx");
        b1.setEmail("username");
        b2.setPassword("aaa");
        b2.setEmail("ccc");
        entityManager.persistAndFlush(b1);
        entityManager.persistAndFlush(b2);


        List<User> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(User::getId)
                .contains(b1.getId(), b2.getId());
        assertThat(all)
                .hasSize(2)
                .extracting(User::getEmail)
                .contains(b1.getEmail(), b2.getEmail());
    }

    @Test
    void testGivenNoUser_whenFindAll_thenReturnEmpty() {
        List<User> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateUser_thenReturnException() {
        User  x = new User();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

    @Test
    void whenFindingUserByEmailAndPassword_thenReturnOneOrNull() {
        User user = new User("alex200020011@gmail.com", "aaaaa");
        entityManager.persistAndFlush(user);

        assertThat(rep.findByEmailAndPassword("alex200020011@gmail.com", "aaaaa")).isPresent();
        assertThat(rep.findByEmailAndPassword("alex200020011@gmail.com", "bbbbb")).isNotPresent();
    }

    
}