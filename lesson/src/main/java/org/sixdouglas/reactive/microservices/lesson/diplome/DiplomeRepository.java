package org.sixdouglas.reactive.microservices.lesson.diplome;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface DiplomeRepository extends ReactiveCrudRepository<Diplome, Long> {
}
