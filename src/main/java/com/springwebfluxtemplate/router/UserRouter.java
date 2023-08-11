package com.springwebfluxtemplate.router;

import com.springwebfluxtemplate.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

//    @Autowired
//    UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/api/user/getAll"),
                        userHandler::getAllUsers)
                .andRoute(RequestPredicates.GET("/api/user/{id}"),
                        userHandler::getUserById)
                .andRoute(RequestPredicates.POST("/api/user/create")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        userHandler::createUser)
                .andRoute(RequestPredicates.PUT("/api/user/update/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        userHandler::updateUser)
                .andRoute(RequestPredicates.DELETE("/api/user/delete/{id}"),
                        userHandler::deleteUser);
    }
}
