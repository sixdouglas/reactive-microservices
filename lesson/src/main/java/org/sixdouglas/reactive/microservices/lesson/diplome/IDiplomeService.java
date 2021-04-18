package org.sixdouglas.reactive.microservices.lesson.diplome;

import reactor.core.publisher.Mono;

public interface IDiplomeService {
    Mono<Boolean> diplomeExistsById(Long diplomeId);
}
