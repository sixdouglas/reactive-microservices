package org.sixdouglas.reactive.microservices.notation.note;

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
class NoteEndpointConfiguration {

    @Bean("noteRoutes")
    RouterFunction<ServerResponse> routes(NoteHandler handler) {
        return route(GET("/api/notes"), handler::all)
                .andRoute(GET("/api/notes/{id}"), handler::getById)
                .andRoute(DELETE("/api/notes/{id}"), handler::deleteById)
                .andRoute(POST("/api/notes"), handler::create)
                .andRoute(PUT("/api/notes/{id}"), handler::updateById);
    }
}
