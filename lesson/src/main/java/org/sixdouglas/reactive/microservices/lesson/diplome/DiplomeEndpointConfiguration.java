package org.sixdouglas.reactive.microservices.lesson.diplome;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
class DiplomeEndpointConfiguration {

    @Bean("diplomeRoutes")
    RouterFunction<ServerResponse> routes(DiplomeHandler handler) {
        return route(GET("/api/diplomes"), handler::all)
                .andRoute(GET("/api/diplomes/{id}"), handler::getById)
                .andRoute(DELETE("/api/diplomes/{id}"), handler::deleteById)
                .andRoute(POST("/api/diplomes"), handler::create)
                .andRoute(PUT("/api/diplomes/{id}"), handler::updateById);
    }
}
