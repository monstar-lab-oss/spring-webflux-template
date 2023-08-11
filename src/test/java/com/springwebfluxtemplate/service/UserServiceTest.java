package com.springwebfluxtemplate.service;

import com.springwebfluxtemplate.entity.User;
import com.springwebfluxtemplate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User(1L, "John", "Doe");
        User user2 = new User(2L, "Jane", "Smith");

        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2).log());

        Flux<User> userFlux = userService.getAllUsers();

        StepVerifier.create(userFlux)
                .expectNext(user1)
                .expectNext(user2)
                .verifyComplete();
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "John", "Doe");

        when(userRepository.findById(anyLong())).thenReturn(Mono.just(user));

        Mono<User> userMono = userService.getUserById(1L);

        StepVerifier.create(userMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    public void testSaveUser() {
        User user = new User("John", "Doe");

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> savedUserMono = userService.saveUser(user);

        StepVerifier.create(savedUserMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.deleteById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> deleteMono = userService.deleteUser(1L);

        StepVerifier.create(deleteMono)
                .verifyComplete();
    }

}
