package org.sixdouglas.reactive.microservices.notation.devoir;

import reactor.core.publisher.Mono;

public interface IDevoirService {
    Mono<Boolean> devoirExistsById(Long devoirId);
}
