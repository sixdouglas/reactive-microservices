package org.sixdouglas.reactive.microservices.lesson.annee;

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
class AnneeEndpointConfiguration {

    @Bean("anneeRoutes")
    RouterFunction<ServerResponse> routes(AnneeHandler handler) {
        return route(GET("/api/annees"), handler::all)
                .andRoute(GET("/api/annees/{id}"), handler::getById)
                .andRoute(DELETE("/api/annees/{id}"), handler::deleteById)
                .andRoute(POST("/api/annees"), handler::create)
                .andRoute(PUT("/api/annees/{id}"), handler::updateById);
    }
}
