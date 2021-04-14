package org.sixdouglas.reactive.microservices.lesson.annee;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface AnneeRepository extends ReactiveCrudRepository<Annee, Long> {
}
