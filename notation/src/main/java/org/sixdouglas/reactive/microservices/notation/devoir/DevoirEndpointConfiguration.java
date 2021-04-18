package org.sixdouglas.reactive.microservices.notation.devoir;

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
class DevoirEndpointConfiguration {

    @Bean("devoirRoutes")
    RouterFunction<ServerResponse> routes(DevoirHandler handler) {
        return route(GET("/api/devoirs"), handler::all)
                .andRoute(GET("/api/devoirs/{id}"), handler::getById)
                .andRoute(DELETE("/api/devoirs/{id}"), handler::deleteById)
                .andRoute(POST("/api/devoirs"), handler::create)
                .andRoute(PUT("/api/devoirs/{id}"), handler::updateById);
    }
}
