package com.springwebfluxtemplate.integration;

import com.springwebfluxtemplate.entity.User;
import com.springwebfluxtemplate.repository.UserRepository;
import com.springwebfluxtemplate.request.UserRequest;
import com.springwebfluxtemplate.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureWebTestClient
public class UserIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll().thenMany(
                userRepository.saveAll(Flux.just(
                        new User("John", "Doe"),
                        new User("Jane", "Smith")
                ))
        ).blockLast();
    }

    @Test
    public void testGetAllUsers() {
        webTestClient.get().uri("/api/user/getAll")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponse.class)
                .hasSize(2)
                .consumeWith(response -> {
                    List<UserResponse> users = response.getResponseBody();
                    assertNotNull(users);
                    assertEquals("John", users.get(0).getFirstname());
                    assertEquals("Doe", users.get(0).getLastname());
                });
    }

    @Test
    public void testGetUserById() {
        Long userId = userRepository.findAll().blockFirst().getId();
        webTestClient.get().uri("/api/user/" + userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .consumeWith(response -> {
                    UserResponse user = response.getResponseBody();
                    assertNotNull(user);
                    assertEquals("John", user.getFirstname());
                    assertEquals("Doe", user.getLastname());
                });
    }

    @Test
    public void testCreateUser() {
        UserRequest newUser = new UserRequest("Alice", "Johnson");
        webTestClient.post().uri("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newUser), UserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .consumeWith(response -> {
                    UserResponse user = response.getResponseBody();
                    assertNotNull(user);
                    assertEquals("Alice", user.getFirstname());
                    assertEquals("Johnson", user.getLastname());
                });
    }

}


