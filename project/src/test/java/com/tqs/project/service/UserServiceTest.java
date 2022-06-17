package com.tqs.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.tqs.project.model.User;
import com.tqs.project.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock(lenient = true)
    private UserRepository rep;

    @InjectMocks
    private UserService service;

    @BeforeEach
    public void setUp() {
        User john = new User("123beer456", "jonikings");
        john.setId(111L);

        User gon = new User("3trwef4", "FuraM");
        User peter = new User("jdh2749j", "PeterPain");
        User alex = new User("noknok", "Alex");

        List<User> allUsers = Arrays.asList(john, gon, alex, peter);

        Mockito.when(rep.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(rep.findAll()).thenReturn(allUsers);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSaveUser_thenReturnUser() {
        User user = new User();
        Mockito.when(rep.save(user)).thenReturn(user);

        assertThat(service.save(user)).isEqualTo(user);

        Mockito.verify(rep, VerificationModeFactory.times(1)).save(user);
    }

    @Test
    void whenSearchUserId_thenUserShouldBeFound() {
        Optional<User> found = service.getUserById( 111L );
        User user = null;
        if (found.isPresent()) user = found.get();

        assertThat(user.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void whenSearchInvalidId_thenUserShouldNotBeFound() {
        Optional<User> found = service.getUserById(-99L);
        User user = null;
        if (found.isPresent()) user = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(user).isNull();
    }

    @Test
    void whengetAll_thenReturn4Records() {
        User john = new User("123beer456", "jonikings");
        User gon = new User("3trwef4", "FuraM");
        User peter = new User("jdh2749j", "PeterPain");
        User alex = new User("noknok", "Alex");

        List<User> allUsers = service.getAllUsers();

        // verify if FindAllUsers is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allUsers).hasSize(4).extracting(User::getEmail).contains(alex.getEmail(), john.getEmail(),
                peter.getEmail(), gon.getEmail());
    }

    @Test
    void whenFindingByUsernameAndPassword_thenReturnsValidOptional() {
        Mockito.when(rep.findByEmailAndPassword("email", "password")).thenReturn(Optional.of(new User()));
        Mockito.when(rep.findByEmailAndPassword("email", "password2")).thenReturn(Optional.empty());

        assertThat(service.getByEmailAndPassword("email", "password")).isPresent();
        assertThat(service.getByEmailAndPassword("email", "password2")).isNotPresent();

        Mockito.verify(rep, VerificationModeFactory.times(1)).findByEmailAndPassword("email", "password");
        Mockito.verify(rep, VerificationModeFactory.times(1)).findByEmailAndPassword("email", "password2");
    }

    @Test
    void whenGettingAuthenticatedUser_ThenReturnsValidOptional() {
        assertThat(service.getAuthenticatedUser().getClass()).isEqualTo(Optional.class);
    }
 }