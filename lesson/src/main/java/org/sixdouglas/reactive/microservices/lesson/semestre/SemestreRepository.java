package org.sixdouglas.reactive.microservices.lesson.semestre;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface SemestreRepository extends ReactiveCrudRepository<Semestre, Long> {
}
