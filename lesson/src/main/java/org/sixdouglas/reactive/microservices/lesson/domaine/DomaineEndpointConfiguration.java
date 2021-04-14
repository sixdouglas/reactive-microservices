package org.sixdouglas.reactive.microservices.lesson.domaine;

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
class DomaineEndpointConfiguration {

    @Bean("domaineRoutes")
    RouterFunction<ServerResponse> routes(DomaineHandler handler) {
        return route(GET("/api/domaines"), handler::all)
                .andRoute(GET("/api/domaines/{id}"), handler::getById)
                .andRoute(DELETE("/api/domaines/{id}"), handler::deleteById)
                .andRoute(POST("/api/domaines"), handler::create)
                .andRoute(PUT("/api/domaines/{id}"), handler::updateById);
    }
}
