package org.sixdouglas.reactive.microservices.lesson.matiere;

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
class MatiereHandler {

    private final MatiereService matiereService;
    
    Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.matiereService.get(id(r)));
    }

    Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(this.matiereService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.matiereService.delete(id(r)));
    }

    Mono<ServerResponse> updateById(ServerRequest r) {
        Flux<Matiere> id = r.bodyToFlux(Matiere.class)
                .flatMap(p -> this.matiereService.update(id(r), p.getName(), p.getSemestreId(), p.getEnseignantId()));
        return defaultReadResponse(id);
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Matiere> flux = request
                .bodyToFlux(Matiere.class)
                .flatMap(toWrite -> this.matiereService.create(toWrite.getName(), toWrite.getSemestreId(), toWrite.getEnseignantId()));
        return defaultWriteResponse(flux);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Matiere> annees) {
        return Mono
                .from(annees)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/api/semestres/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Matiere> diplomes) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(diplomes, Matiere.class);
    }

    private static Long id(ServerRequest r) {
        return Long.parseUnsignedLong(r.pathVariable("id"));
    }}
