package org.sixdouglas.reactive.microservices.lesson.matiere;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sixdouglas.reactive.microservices.lesson.semestre.ISemestreService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
class MatiereService {

    private final MatiereRepository matiereRepository;
    private final ISemestreService iSemestreService;

    public Flux<Matiere> all() {
        return this.matiereRepository.findAll();
    }

    public Mono<Matiere> get(Long id) {
        return this.matiereRepository.findById(id);
    }

    public Mono<Matiere> update(Long id, String name, Long semestreId, String enseignantId) {
        return this.matiereRepository
                .findById(id)
                .flatMap(matiere -> checkSemestreIdAndReturn(semestreId, matiere))
                .map(p -> {
                    p.setName(name);
                    p.setSemestreId(semestreId);
                    p.setEnseignantId(enseignantId);
                    return p;
                })
                .flatMap(this.matiereRepository::save);
    }

    public Mono<Matiere> delete(Long id) {
        return this.matiereRepository
                .findById(id)
                .flatMap(p -> this.matiereRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Matiere> create(String name, Long semestreId, String enseignantId) {
        return checkSemestreId(semestreId)
                .flatMap(exists -> this.matiereRepository.save(new Matiere(null, name, semestreId, enseignantId)));
    }

    private Mono<Matiere> checkSemestreIdAndReturn(Long semestreId, Matiere matiere) {
        return checkSemestreId(semestreId).thenReturn(matiere);
    }

    private Mono<Boolean> checkSemestreId(Long semestreId) {
        return iSemestreService.semestreExistsById(semestreId);
    }
}
