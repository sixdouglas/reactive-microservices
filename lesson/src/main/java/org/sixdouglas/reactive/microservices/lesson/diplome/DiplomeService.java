package org.sixdouglas.reactive.microservices.lesson.diplome;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class DiplomeService {

    private final DiplomeRepository diplomeRepository;

    public Flux<Diplome> all() {
        return this.diplomeRepository.findAll();
    }

    public Mono<Diplome> get(Long id) {
        return this.diplomeRepository.findById(id);
    }

    public Mono<Diplome> update(Long id, String name, Long domaineId, Integer ordre) {
        return this.diplomeRepository
                .findById(id)
                .map(p -> new Diplome(p.getId(), name, domaineId, ordre))
                .flatMap(this.diplomeRepository::save);
    }

    public Mono<Diplome> delete(Long id) {
        return this.diplomeRepository
                .findById(id)
                .flatMap(p -> this.diplomeRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Diplome> create(String name, Long domaineId, Integer ordre) {
        return this.diplomeRepository
                .save(new Diplome(null, name, domaineId, ordre));
    }

}
