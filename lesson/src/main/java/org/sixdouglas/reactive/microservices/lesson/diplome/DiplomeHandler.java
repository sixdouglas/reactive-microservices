package org.sixdouglas.reactive.microservices.lesson.diplome;

import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@AllArgsConstructor
@Component
class DiplomeHandler {

    private final DiplomeService diplomeService;
    
    Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.diplomeService.get(id(r)));
    }

    Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(this.diplomeService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.diplomeService.delete(id(r)));
    }

    Mono<ServerResponse> updateById(ServerRequest r) {
        Flux<Diplome> id = r.bodyToFlux(Diplome.class)
                .flatMap(p -> this.diplomeService.update(id(r), p.getName(), p.getDomaineId(), p.getOrdre()));
        return defaultReadResponse(id);
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Diplome> flux = request
                .bodyToFlux(Diplome.class)
                .flatMap(toWrite -> this.diplomeService.create(toWrite.getName(), toWrite.getDomaineId(), toWrite.getOrdre()));
        return defaultWriteResponse(flux);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Diplome> annees) {
        return Mono
                .from(annees)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/api/diplomes/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Diplome> diplomes) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(diplomes, Diplome.class);
    }

    private static Long id(ServerRequest r) {
        return Long.parseUnsignedLong(r.pathVariable("id"));
    }}
