package org.sixdouglas.reactive.microservices.lesson.matiere;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Data
class Matiere {
    @Id
    private Long id;
    private String name;
    private Long semestreId;
    private String enseignantId;
}
