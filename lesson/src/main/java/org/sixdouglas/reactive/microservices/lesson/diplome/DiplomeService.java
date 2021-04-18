package org.sixdouglas.reactive.microservices.lesson.diplome;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sixdouglas.reactive.microservices.lesson.domaine.IDomaineService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class DiplomeService implements IDiplomeService {

    private final DiplomeRepository diplomeRepository;
    private final IDomaineService iDomaineService;

    public Flux<Diplome> all() {
        return this.diplomeRepository.findAll();
    }

    public Mono<Diplome> get(Long id) {
        return this.diplomeRepository.findById(id);
    }

    public Mono<Diplome> update(Long id, String name, Long domaineId, Integer ordre) {
        return this.diplomeRepository
                .findById(id)
                .flatMap(diplome -> checkDomaineIdAndReturn(domaineId, diplome))
                .map(p ->  {
                    p.setName(name);
                    p.setDomaineId(domaineId);
                    p.setOrdre(ordre);
                    return p;
                })
                .flatMap(this.diplomeRepository::save);
    }

    public Mono<Diplome> delete(Long id) {
        return this.diplomeRepository
                .findById(id)
                .flatMap(p -> this.diplomeRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Diplome> create(String name, Long domaineId, Integer ordre) {
        return checkDomaineId(domaineId)
                .flatMap(exists -> diplomeRepository.save(new Diplome(null, name, domaineId, ordre)));
    }

    private Mono<Diplome> checkDomaineIdAndReturn(Long domaineId, Diplome diplome) {
        return checkDomaineId(domaineId).thenReturn(diplome);
    }

    private Mono<Boolean> checkDomaineId(Long domaineId) {
        return iDomaineService.domaineExistsById(domaineId);
    }

    @Override
    public Mono<Boolean> diplomeExistsById(Long diplomeId) {
        return this.diplomeRepository.existsById(diplomeId);
    }
}
