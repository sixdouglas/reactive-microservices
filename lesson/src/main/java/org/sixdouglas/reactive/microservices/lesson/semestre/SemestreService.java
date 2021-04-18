package org.sixdouglas.reactive.microservices.lesson.semestre;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sixdouglas.reactive.microservices.lesson.annee.IAnneeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class SemestreService implements ISemestreService {

    private final SemestreRepository semestreRepository;
    private final IAnneeService iAnneeService;

    public Flux<Semestre> all() {
        return this.semestreRepository.findAll();
    }

    public Mono<Semestre> get(Long id) {
        return this.semestreRepository.findById(id);
    }

    public Mono<Semestre> update(Long id, String name, Long anneeId, Integer ordre) {
        return this.semestreRepository
                .findById(id)
                .flatMap(semestre -> checkAnneeIdAndReturn(anneeId, semestre))
                .map(p -> {
                    p.setName(name);
                    p.setAnneeId(anneeId);
                    p.setOrdre(ordre);
                    return p;
                })
                .flatMap(this.semestreRepository::save);
    }

    public Mono<Semestre> delete(Long id) {
        return this.semestreRepository
                .findById(id)
                .flatMap(p -> this.semestreRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Semestre> create(String name, Long anneeId, Integer ordre) {
        return checkAnneeId(anneeId)
                .flatMap(exists -> this.semestreRepository.save(new Semestre(null, name, anneeId, ordre)));
    }

    private Mono<Semestre> checkAnneeIdAndReturn(Long anneeId, Semestre semestre) {
        return checkAnneeId(anneeId).thenReturn(semestre);
    }

    private Mono<Boolean> checkAnneeId(Long domaineId) {
        return iAnneeService.anneeExistsById(domaineId);
    }

    @Override
    public Mono<Boolean> semestreExistsById(Long semestreId) {
        return semestreRepository.existsById(semestreId);
    }
}
