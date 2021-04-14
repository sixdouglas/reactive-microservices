package org.sixdouglas.reactive.microservices.lesson.matiere;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class MatiereService {

    private final MatiereRepository matiereRepository;

    public Flux<Matiere> all() {
        return this.matiereRepository.findAll();
    }

    public Mono<Matiere> get(Long id) {
        return this.matiereRepository.findById(id);
    }

    public Mono<Matiere> update(Long id, String name, Long semestreId, String enseignantId) {
        return this.matiereRepository
                .findById(id)
                .map(p -> new Matiere(p.getId(), name, semestreId, enseignantId))
                .flatMap(this.matiereRepository::save);
    }

    public Mono<Matiere> delete(Long id) {
        return this.matiereRepository
                .findById(id)
                .flatMap(p -> this.matiereRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Matiere> create(String name, Long semestreId, String enseignantId) {
        return this.matiereRepository
                .save(new Matiere(null, name, semestreId, enseignantId));
    }

}
