package com.springwebfluxtemplate.handler;

import com.springwebfluxtemplate.request.UpdateUserRequest;
import com.springwebfluxtemplate.request.UserRequest;
import com.springwebfluxtemplate.response.UserResponse;
import com.springwebfluxtemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserService userService;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)  // It can be used TEXT_EVENT_STREAM as well to get as stream
                .body(userService.getAllUsers()
                        .map(user -> userService.mapUserToUserResponse(user)), //You can put log() to  see stream and put delay to see one by one streaming
                        UserResponse.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getUserById(id)
                        .map(user -> userService.mapUserToUserResponse(user)), UserResponse.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        Mono<UserRequest> monoUserRequest = serverRequest.bodyToMono(UserRequest.class);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(monoUserRequest
                        .map(userService::mapUserRequestToUser)
                        .flatMap(user -> userService.saveUser(user))
                        .map(user -> userService.mapUserToUserResponse(user)), UserResponse.class);

    }

    public Mono<ServerResponse> updateUser (ServerRequest serverRequest) {
        Mono<UpdateUserRequest> monoUpdateUserRequest = serverRequest.bodyToMono(UpdateUserRequest.class);

        Long id = Long.valueOf(serverRequest.pathVariable("id"));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        userService.getUserById(id) // Mono user 1 - {1: Muhittin KAYA}
                        .flatMap(user ->
                                monoUpdateUserRequest
                                .map(request -> userService.mapUpdateUserRequestToUser(user, request))) //Request -> firstname: "" lastname: "" update in here
                        .flatMap(user -> userService.saveUser(user)) // save to repo user
                                .map(user -> userService.mapUserToUserResponse(user)), // map user to user response to return
                        UserResponse.class
                );

    }

    public Mono<ServerResponse> deleteUser (ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.deleteUser(id)
                                .then(Mono.fromCallable(() -> "User has been deleted!")),
                        String.class);

    }

}
