package com.springwebfluxtemplate.service;

import com.springwebfluxtemplate.request.UpdateUserRequest;
import com.springwebfluxtemplate.request.UserRequest;
import com.springwebfluxtemplate.response.UserResponse;
import com.springwebfluxtemplate.entity.User;
import com.springwebfluxtemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse mapUserToUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstname(), user.getLastname());
    }

    public Mono<User> getUserById (Long id) {
        return userRepository.findById(id);
    }

    public User mapUserRequestToUser (UserRequest userRequest) {
        return new User(userRequest.getFirstname(), userRequest.getLastname());
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    public User mapUpdateUserRequestToUser(User user, UpdateUserRequest updateUserRequest) {
        user.setFirstname(updateUserRequest.getFirstname());
        user.setLastname(updateUserRequest.getLastname());

        return user;
    }

    public Mono<Void> deleteUser (Long id) {
        return userRepository.deleteById(id);
    }
}
