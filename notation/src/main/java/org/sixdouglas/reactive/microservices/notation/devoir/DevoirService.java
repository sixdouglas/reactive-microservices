package org.sixdouglas.reactive.microservices.notation.devoir;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
class DevoirService {

    private final DevoirRepository devoirRepository;

    public Flux<Devoir> all() {
        return this.devoirRepository.findAll();
    }

    public Mono<Devoir> get(Long id) {
        return this.devoirRepository.findById(id);
    }

    public Mono<Devoir> update(Long id, String name, Long matiereId, LocalDateTime dateTime) {
        return this.devoirRepository
                .findById(id)
                .map(p -> {
                    p.setName(name);
                    p.setMatiereId(matiereId);
                    p.setDateTime(dateTime);
                    return p;
                })
                .flatMap(this.devoirRepository::save);
    }

    public Mono<Devoir> delete(Long id) {
        return this.devoirRepository
                .findById(id)
                .flatMap(p -> this.devoirRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Devoir> create(String name, Long matiereId, LocalDateTime dateTime) {
        return this.devoirRepository.save(new Devoir(null, name, dateTime, matiereId));
    }

}
