package org.sixdouglas.reactive.microservices.lesson.annee;

import reactor.core.publisher.Mono;

public interface IAnneeService {
    Mono<Boolean> anneeExistsById(Long anneeId);
}
