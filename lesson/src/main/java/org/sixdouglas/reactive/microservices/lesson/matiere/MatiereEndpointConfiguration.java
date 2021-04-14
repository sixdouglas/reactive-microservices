package org.sixdouglas.reactive.microservices.lesson.matiere;

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
class MatiereEndpointConfiguration {

    @Bean("matiereRoutes")
    RouterFunction<ServerResponse> routes(MatiereHandler handler) {
        return route(GET("/api/matieres"), handler::all)
                .andRoute(GET("/api/matieres/{id}"), handler::getById)
                .andRoute(DELETE("/api/matieres/{id}"), handler::deleteById)
                .andRoute(POST("/api/matieres"), handler::create)
                .andRoute(PUT("/api/matieres/{id}"), handler::updateById);
    }
}
