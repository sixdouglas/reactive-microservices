package org.sixdouglas.reactive.microservices.notation.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sixdouglas.reactive.microservices.notation.devoir.IDevoirService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Log4j2
@RequiredArgsConstructor
@Service
class NoteService {

    private final NoteRepository noteRepository;
    private final IDevoirService iDevoirService;

    public Flux<Note> all() {
        return this.noteRepository.findAll();
    }

    public Mono<Note> get(Long id) {
        return this.noteRepository.findById(id);
    }

    public Mono<Note> update(Long id, Long devoirId, BigDecimal note, String remarque, String eleveId) {
        return this.noteRepository
                .findById(id)
                .flatMap(currentNote -> checkDevoirIdAndReturn(devoirId, currentNote))
                .map(p -> {
                    p.setNote(note);
                    p.setDevoirId(devoirId);
                    p.setRemarque(remarque);
                    p.setEleveId(eleveId);
                    return p;
                })
                .flatMap(this.noteRepository::save);
    }

    public Mono<Note> delete(Long id) {
        return this.noteRepository
                .findById(id)
                .flatMap(p -> this.noteRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Note> create(Long devoirId, BigDecimal note, String remarque, String eleveId) {
        return checkDevoirId(devoirId)
                .flatMap(exists -> this.noteRepository.save(new Note(null, devoirId, note, remarque, eleveId)));
    }

    private Mono<Note> checkDevoirIdAndReturn(Long devoirId, Note note) {
        return checkDevoirId(devoirId).thenReturn(note);
    }

    private Mono<Boolean> checkDevoirId(Long devoirId) {
        return iDevoirService.devoirExistsById(devoirId);
    }
}
