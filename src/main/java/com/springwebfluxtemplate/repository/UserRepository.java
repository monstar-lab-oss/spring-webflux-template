package com.springwebfluxtemplate.repository;

import com.springwebfluxtemplate.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
