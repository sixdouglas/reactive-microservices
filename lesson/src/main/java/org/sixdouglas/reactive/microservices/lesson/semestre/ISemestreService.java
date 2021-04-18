package org.sixdouglas.reactive.microservices.lesson.semestre;

import reactor.core.publisher.Mono;

public interface ISemestreService {
    Mono<Boolean> semestreExistsById(Long semestreId);
}
