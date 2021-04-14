package org.sixdouglas.reactive.microservices.lesson.semestre;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class SemestreService {

    private final SemestreRepository semestreRepository;

    public Flux<Semestre> all() {
        return this.semestreRepository.findAll();
    }

    public Mono<Semestre> get(Long id) {
        return this.semestreRepository.findById(id);
    }

    public Mono<Semestre> update(Long id, String name, Long semestreId, Integer ordre) {
        return this.semestreRepository
                .findById(id)
                .map(p -> new Semestre(p.getId(), name, semestreId, ordre))
                .flatMap(this.semestreRepository::save);
    }

    public Mono<Semestre> delete(Long id) {
        return this.semestreRepository
                .findById(id)
                .flatMap(p -> this.semestreRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Semestre> create(String name, Long semestreId, Integer ordre) {
        return this.semestreRepository
                .save(new Semestre(null, name, semestreId, ordre));
    }

}
