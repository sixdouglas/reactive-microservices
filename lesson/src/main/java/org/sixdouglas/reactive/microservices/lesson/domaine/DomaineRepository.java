package org.sixdouglas.reactive.microservices.lesson.domaine;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface DomaineRepository extends ReactiveCrudRepository<Domaine, Long> {
}
