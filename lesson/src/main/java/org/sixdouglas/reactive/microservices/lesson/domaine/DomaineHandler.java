package org.sixdouglas.reactive.microservices.lesson.domaine;

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
class DomaineHandler {

    private final DomaineService domaineService;
    
    Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.domaineService.get(id(r)));
    }

    Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(this.domaineService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.domaineService.delete(id(r)));
    }

    Mono<ServerResponse> updateById(ServerRequest r) {
        Flux<Domaine> id = r.bodyToFlux(Domaine.class)
                .flatMap(p -> this.domaineService.update(id(r), p.getName(), p.getOrdre()));
        return defaultReadResponse(id);
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Domaine> flux = request
                .bodyToFlux(Domaine.class)
                .flatMap(toWrite -> this.domaineService.create(toWrite.getName(), toWrite.getOrdre()));
        return defaultWriteResponse(flux);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Domaine> annees) {
        return Mono
                .from(annees)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/api/domaines/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Domaine> domainePublisher) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(domainePublisher, Domaine.class);
    }

    private static Long id(ServerRequest r) {
        return Long.parseUnsignedLong(r.pathVariable("id"));
    }}
