package org.sixdouglas.reactive.microservices.notation.devoir;

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
class DevoirHandler {

    private final DevoirService devoirService;
    
    Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.devoirService.get(id(r)));
    }

    Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(this.devoirService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.devoirService.delete(id(r)));
    }

    Mono<ServerResponse> updateById(ServerRequest r) {
        Flux<Devoir> id = r.bodyToFlux(Devoir.class)
                .flatMap(p -> this.devoirService.update(id(r), p.getName(), p.getMatiereId(), p.getDateTime()));
        return defaultReadResponse(id);
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Devoir> flux = request
                .bodyToFlux(Devoir.class)
                .flatMap(toWrite -> this.devoirService.create(toWrite.getName(), toWrite.getMatiereId(), toWrite.getDateTime()));
        return defaultWriteResponse(flux);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Devoir> annees) {
        return Mono
                .from(annees)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/api/devoirs/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Devoir> diplomes) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(diplomes, Devoir.class);
    }

    private static Long id(ServerRequest r) {
        return Long.parseUnsignedLong(r.pathVariable("id"));
    }}
