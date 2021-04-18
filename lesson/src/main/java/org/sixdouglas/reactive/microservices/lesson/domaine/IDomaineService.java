package org.sixdouglas.reactive.microservices.lesson.domaine;

import reactor.core.publisher.Mono;

public interface IDomaineService {
    Mono<Boolean> domaineExistsById(Long domaineId);
}
