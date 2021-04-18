package org.sixdouglas.reactive.microservices.lesson.annee;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sixdouglas.reactive.microservices.lesson.diplome.IDiplomeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class AnneeService implements IAnneeService {

    private final AnneeRepository anneeRepository;
    private final IDiplomeService iDiplomeService;

    public Flux<Annee> all() {
        return this.anneeRepository.findAll();
    }

    public Mono<Annee> get(Long id) {
        return this.anneeRepository.findById(id);
    }

    public Mono<Annee> update(Long id, String name, Long diplomeId, Integer ordre) {
        return this.anneeRepository
                .findById(id)
                .flatMap(annee -> checkDiplomeIdAndReturn(diplomeId, annee))
                .map(p -> {
                    p.setName(name);
                    p.setDiplomeId(diplomeId);
                    p.setOrdre(ordre);
                    return p;
                })
                .flatMap(this.anneeRepository::save);
    }

    public Mono<Annee> delete(Long id) {
        return this.anneeRepository
                .findById(id)
                .flatMap(p -> this.anneeRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Annee> create(String name, Long diplomeId, Integer ordre) {
        return checkDiplomeId(diplomeId)
                .flatMap(exists -> anneeRepository.save(new Annee(null, name, diplomeId, ordre)));
    }


    private Mono<Annee> checkDiplomeIdAndReturn(Long diplomeId, Annee annee) {
        return checkDiplomeId(diplomeId).thenReturn(annee);
    }

    private Mono<Boolean> checkDiplomeId(Long diplomeId) {
        return iDiplomeService.diplomeExistsById(diplomeId);
    }

    @Override
    public Mono<Boolean> anneeExistsById(Long anneeId) {
        return anneeRepository.existsById(anneeId);
    }
}
