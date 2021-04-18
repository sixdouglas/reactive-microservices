package org.sixdouglas.reactive.microservices.notation.note;

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
class NoteHandler {

    private final NoteService noteService;
    
    Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.noteService.get(id(r)));
    }

    Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(this.noteService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.noteService.delete(id(r)));
    }

    Mono<ServerResponse> updateById(ServerRequest r) {
        Flux<Note> id = r.bodyToFlux(Note.class)
                .flatMap(p -> this.noteService.update(id(r), p.getDevoirId(), p.getNote(), p.getRemarque(), p.getEleveId()));
        return defaultReadResponse(id);
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Note> flux = request
                .bodyToFlux(Note.class)
                .flatMap(toWrite -> this.noteService.create(toWrite.getDevoirId(), toWrite.getNote(), toWrite.getRemarque(), toWrite.getEleveId()));
        return defaultWriteResponse(flux);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Note> annees) {
        return Mono
                .from(annees)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/api/notes/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Note> diplomes) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(diplomes, Note.class);
    }

    private static Long id(ServerRequest r) {
        return Long.parseUnsignedLong(r.pathVariable("id"));
    }}
