package org.sixdouglas.reactive.microservices.lesson.semestre;

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
class SemestreEndpointConfiguration {

    @Bean("semestreRoutes")
    RouterFunction<ServerResponse> routes(SemestreHandler handler) {
        return route(GET("/api/semestres"), handler::all)
                .andRoute(GET("/api/semestres/{id}"), handler::getById)
                .andRoute(DELETE("/api/semestres/{id}"), handler::deleteById)
                .andRoute(POST("/api/semestres"), handler::create)
                .andRoute(PUT("/api/semestres/{id}"), handler::updateById);
    }
}
