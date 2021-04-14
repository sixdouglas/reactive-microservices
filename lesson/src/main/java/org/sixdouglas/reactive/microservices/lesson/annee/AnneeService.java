package org.sixdouglas.reactive.microservices.lesson.annee;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class AnneeService {

    private final AnneeRepository anneeRepository;

    public Flux<Annee> all() {
        return this.anneeRepository.findAll();
    }

    public Mono<Annee> get(Long id) {
        return this.anneeRepository.findById(id);
    }

    public Mono<Annee> update(Long id, String name, Long diplomeId, Integer ordre) {
        return this.anneeRepository
                .findById(id)
                .map(p -> new Annee(p.getId(), name, diplomeId, ordre))
                .flatMap(this.anneeRepository::save);
    }

    public Mono<Annee> delete(Long id) {
        return this.anneeRepository
                .findById(id)
                .flatMap(p -> this.anneeRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Annee> create(String name, Long diplomeId, Integer ordre) {
        return this.anneeRepository
                .save(new Annee(null, name, diplomeId, ordre));
    }

}
