package org.sixdouglas.reactive.microservices.lesson.domaine;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class DomaineService implements IDomaineService {

    private final DomaineRepository domaineRepository;

    public Flux<Domaine> all() {
        return this.domaineRepository.findAll();
    }

    public Mono<Domaine> get(Long id) {
        return this.domaineRepository.findById(id);
    }

    public Mono<Domaine> update(Long id, String name, Integer ordre) {
        return this.domaineRepository
                .findById(id)
                .map(p -> {
                    p.setName(name);
                    p.setOrdre(ordre);
                    return p;
                })
                .flatMap(this.domaineRepository::save);
    }

    public Mono<Domaine> delete(Long id) {
        return this.domaineRepository
                .findById(id)
                .flatMap(p -> this.domaineRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Domaine> create(String name, Integer ordre) {
        return this.domaineRepository
                .save(new Domaine(null, name, ordre));
    }

    @Override
    public Mono<Boolean> domaineExistsById(Long domaineId) {
        return this.domaineRepository.existsById(domaineId);
    }
}
