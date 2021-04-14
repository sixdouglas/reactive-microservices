package org.sixdouglas.reactive.microservices.lesson.matiere;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface MatiereRepository extends ReactiveCrudRepository<Matiere, Long> {
}
