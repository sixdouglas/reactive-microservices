package org.sixdouglas.reactive.microservices.notation.note;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface NoteRepository extends ReactiveSortingRepository<Note, Long> {
}
