package org.sixdouglas.reactive.microservices.notation.devoir;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface DevoirRepository extends ReactiveSortingRepository<Devoir, Long> {
}
